package com.github.yasminmiguez.ms_pagamento.service;

import com.github.yasminmiguez.ms_pagamento.dto.PagamentoDTO;
import com.github.yasminmiguez.ms_pagamento.entity.Pagamento;
import com.github.yasminmiguez.ms_pagamento.repository.PagamentoRepository;
import com.github.yasminmiguez.ms_pagamento.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {


    @Autowired
    private PagamentoRepository repository;

@Transactional(readOnly = true)
    public List<PagamentoDTO> getAll(){
        List<Pagamento> pagamentos = repository.findAll();
        return pagamentos.stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO getById(Long id){
    Pagamento entity = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Recurso não encontrado: ID ")
    );
    return new PagamentoDTO(entity);

    }







}
