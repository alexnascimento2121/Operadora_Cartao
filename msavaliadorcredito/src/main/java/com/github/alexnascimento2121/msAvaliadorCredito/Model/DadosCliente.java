package com.github.alexnascimento2121.msAvaliadorCredito.Model;

import lombok.Data;

@Data // representacao do dados clientes do projeto mscliente - classe pojo
public class DadosCliente {
    private Long id;
    private String nome;
    private Integer idade;
}
