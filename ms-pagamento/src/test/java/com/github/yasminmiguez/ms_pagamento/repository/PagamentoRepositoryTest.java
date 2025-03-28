package com.github.yasminmiguez.ms_pagamento.repository;

import com.github.yasminmiguez.ms_pagamento.entity.Pagamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        //Arrange
        Long existingId = 1L;
        //Act
        repository.deleteById(existingId);
        //Assert
        Optional<Pagamento> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());


    }



}
