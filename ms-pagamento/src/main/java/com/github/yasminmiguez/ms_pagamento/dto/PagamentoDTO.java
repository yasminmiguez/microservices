package com.github.yasminmiguez.ms_pagamento.dto;

import com.github.yasminmiguez.ms_pagamento.entity.Pagamento;
import com.github.yasminmiguez.ms_pagamento.entity.Status;
import com.github.yasminmiguez.ms_pagamento.repository.PagamentoRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class PagamentoDTO {


    private Long id;
    @Positive(message="O valor do pagamento deve ser um número positivo")
    @NotNull (message="Campo obrigatório")
    private BigDecimal valor;
    @Size(min = 2, max=100, message = "O nome deve ser no mínimo 2 e máximo 100 caracteres")
    private String nome;
    @Size(min = 16, max = 19, message = "O número do cartão deve ter entre 16 e 19 caracteres")
    private String numeroDoCartao;

    @Size(max = 5, min=5, message = "A validade do cartão deve ter 5 caracteres")
    private String validade;

    @Size(min = 3, max = 3, message = "O código segurança deve ter 3 caracteres")
    private String codigoDeSeguranca;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Pedido ID é obrigatório")
    private Long pedidoId;

    @NotNull(message = "ID Forma de pagamento é obrigatório")
    private Long formaDePagamentoId;

    public PagamentoDTO(Pagamento entity) {
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        status = entity.getStatus();
        pedidoId = entity.getPedidoId();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }
}
