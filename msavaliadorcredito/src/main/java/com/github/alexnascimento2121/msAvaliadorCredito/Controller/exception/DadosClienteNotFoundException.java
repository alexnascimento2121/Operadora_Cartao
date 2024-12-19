package com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception;

@SuppressWarnings("serial")
public class DadosClienteNotFoundException extends Exception {
	public DadosClienteNotFoundException() {
        super("Dados do cliente não encontrados para o CPF informado.");
    }
}
