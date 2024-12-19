package com.github.alexnascimento2121.mscartoes.controller.request;

import java.math.BigDecimal;

import com.github.alexnascimento2121.mscartoes.model.BandeiraCartao;
import com.github.alexnascimento2121.mscartoes.model.Cartao;

import lombok.Data;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite );
    }
}
