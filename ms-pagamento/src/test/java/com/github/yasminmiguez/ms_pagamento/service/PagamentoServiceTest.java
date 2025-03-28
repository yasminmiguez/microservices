package com.github.yasminmiguez.ms_pagamento.service;

import com.github.yasminmiguez.ms_pagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;


@ExtendWith(SpringExtension.class)
public class PagamentoServiceTest {


    @InjectMocks
    private PagamentoService service;

    @Mock
    private PagamentoRepository repository;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 10L;

        Mockito.when(repository.existsById(existingId)).thenReturn(true);

        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

        Mockito.doNothing().when(repository).deleteById(existingId);
    }

    @Test
    @DisplayName("Delete deveria não fazer nada quando Id existe")

    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(
                () -> {
                    service.deletePagamento(existingId);
                }

        );
    }



}
