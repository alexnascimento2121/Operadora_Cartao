package com.github.alexnascimento2121.msAvaliadorCredito.Model;

import java.math.BigDecimal;

import lombok.Data;

@Data // representacao de dado cliente do projeto cartao
public class CartaoCliente {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}