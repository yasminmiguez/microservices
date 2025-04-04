package com.github.yasminmiguez.ms_pagamento.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yasminmiguez.ms_pagamento.dto.PagamentoDTO;
import com.github.yasminmiguez.ms_pagamento.service.PagamentoService;
import com.github.yasminmiguez.ms_pagamento.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

//importando na mão
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        //get by id
        Mockito.when(service.getById(existingId)).thenReturn(dto);
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




}
