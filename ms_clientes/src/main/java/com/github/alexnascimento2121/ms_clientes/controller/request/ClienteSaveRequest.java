package com.github.alexnascimento2121.ms_clientes.controller.request;

import com.github.alexnascimento2121.ms_clientes.model.Cliente;

import lombok.Data;

@Data
public class ClienteSaveRequest {
	private String cpf;
    private String nome;
    private Integer idade;
    
    // transforma os dados em json para um objeto da classe Client
    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }
}
