package com.github.yasminmiguez.ms_pagamento.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yasminmiguez.ms_pagamento.dto.PagamentoDTO;
import com.github.yasminmiguez.ms_pagamento.service.PagamentoService;
import com.github.yasminmiguez.ms_pagamento.service.exceptions.ResourceNotFoundException;
import com.github.yasminmiguez.ms_pagamento.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.print.attribute.standard.Media;
import java.util.List;

//importando na mão

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PagamentoControllerTest {
    //chamar o endpoint
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean

    private PagamentoService service;
    private PagamentoDTO dto;
    private Long  existingId;
    private Long nonExistingId;

    //converter para JSON  objeto JAVA e enviar na requisição
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        // criando um Pagamento DTO
        dto = Factory.createPagamentoDTO();

        List<PagamentoDTO> list = List.of(dto);

        //simulando o comportamento do service no getAll

        Mockito.when(service.getAll()).thenReturn(list);

        //get by id existente (chama o service no get by id - retorna um dto)
        Mockito.when(service.getById(existingId)).thenReturn(dto);

        // get by id inexistente - lança exception

        Mockito.when(service.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // simulando o comportamento do create pagamento
        // any () simula o comp de qlq objeyo
        Mockito.when(service.createPagamento(any())).thenReturn(dto);

        //simulando update

        //quando id existe
        Mockito.when(service.updatePagamento(eq(existingId),any())).thenReturn(dto);

        // quando id nao existe
        Mockito.when(service.updatePagamento(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);


        //simulando o comportamento do delete pagamento
        //id existe
        Mockito.doNothing().when(service).deletePagamento(existingId);

        //id nao existe
        Mockito.doThrow(ResourceNotFoundException.class).when(service).deletePagamento(nonExistingId);



    }

    //GET DE TUDO - 2 http://localhost:8080/pagamentos
    @Test
    public void getAllShouldReturnAListPagamentoDTO() throws Exception{
        //chamando requisiçã com o método GET no endpoint /pagamentos
        ResultActions result = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

 //CONTROLLER - GET MAPPING, PAGAMENTOS/ID, CHAMA O GET BY ID DOSERVICE, DEPOIS VAI NO REPOSITORY FAZ  FINDBY ID, SE TIVER ELE REORN O AGAMENO DTO SENAO LANÇA A EXCEÇÃO DE RECURSO NAO ENCONTRADO

    //Simular o service by id
       @Test
    public void getByIdShouldReturnPagamentoDTOWhenIdExists() throws Exception {

        ResultActions  result = mockMvc.perform(get("/pagamentos/{id}", existingId).accept(MediaType.APPLICATION_JSON));

        //Assertions
           result.andExpect(status().isOk());
           result.andExpect(jsonPath("$.id").exists());

           result.andExpect(jsonPath("$.valor ").value(32.2));

           result.andExpect(jsonPath("$.status").exists());

       }


    @Test
    public void getByIdShouldThrowResouceNotFoundExcetionWhenIdDoesNotExist() throws Exception {
        ResultActions  result = mockMvc.perform(get("/pagamentos/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void createPagamentoShouldReturnPaamentoDTOCreated() throws Exception{
        // cria = put

        PagamentoDTO newPagamentoDTO = Factory.createNewPagamentoDTO();

                //converter java para json (prga o new pagamento dto e converte para String)

        String jsonRequestBody = objectMapper.writeValueAsString(newPagamentoDTO);


         mockMvc.perform(post("/pagamentos")

                 .content(jsonRequestBody) //request body
                 .contentType(MediaType.APPLICATION_JSON) // request
                 .accept(MediaType.APPLICATION_JSON)) //respnse

                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(header().exists("Location"))
                 .andExpect(jsonPath("$.id").exists())
                 .andExpect(jsonPath("$.valor").exists())
                 .andExpect(jsonPath("$.status").exists())
                 .andExpect(jsonPath("$.pedidoId").exists())
                 .andExpect(jsonPath("$.formaDePagamento").exists());

    }

    @Test
    public void updatePagamentoShouldReturnPagamentoDTOWhenExists() throws Exception {

        String jsonRequestBody = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/pagamentos/{id}", existingId)

                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.pedidoId").exists())
                .andExpect(jsonPath("$.formaDePagamento").exists());


    }


    @Test
    public void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        String jsonRequestBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/pagaments/{id}", nonExistingId)

                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());

    }


    //DELETE

    @Test
    public void deletePagamentShouldDoNothingWhenIdExists() throws Exception{
        mockMvc.perform(delete("/pagamentos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNoContent());
    }


    //DELETE COM ID INEXISTENTE

    @Test
    public void deletePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNoExist() throws Exception{
        mockMvc.perform(delete("/pagamentos{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }



}
