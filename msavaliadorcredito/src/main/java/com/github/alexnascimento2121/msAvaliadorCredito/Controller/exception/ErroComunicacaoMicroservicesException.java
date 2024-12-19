package com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception;

import lombok.Getter;

@SuppressWarnings("serial")
public class ErroComunicacaoMicroservicesException extends Exception {
		@Getter
	    private Integer status;

	    public ErroComunicacaoMicroservicesException(String msg, Integer status) {
	        super(msg);
	        this.status = status;
	    }
}
