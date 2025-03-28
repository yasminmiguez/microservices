package com.github.yasminmiguez.ms_pagamento.repository;

import com.github.yasminmiguez.ms_pagamento.entity.Pagamento;
import com.github.yasminmiguez.ms_pagamento.tests.Factory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository repository;

    //declarando variaveis
    private Long existingId;
    private Long nonExistingId;
    //VERFICICAR QUANTOS REGISTROS TEM NO SEED DO DB
    private Long countTotalPagamento = 3L;


    //Vai ser executado antes de cada teste
    @BeforeEach
    void setup() throws Exception{
        //Arrange
        existingId = 1L;
        nonExistingId = 100L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        //Act
        repository.deleteById(existingId);
        //Assert
        Optional<Pagamento> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());


    }

    @Test
    @DisplayName("Dado parâmetros válidos e Id nulo quando chamar criar pagamento então deve instanciar um Pagamento")
    public void givenValidParamsAndIdIsNull_whenCallCreatePagamento_thenInstantiateAPagamento(){
        Pagamento pagamento = Factory.createPagamento();
        pagamento.setId(null);
        pagamento = repository.save(pagamento);
        Assertions.assertNotNull(pagamento.getId());
        //verifica se o id gerado é o proximo
        Assertions.assertEquals(countTotalPagamento + 1, pagamento.getId());
    }



}
