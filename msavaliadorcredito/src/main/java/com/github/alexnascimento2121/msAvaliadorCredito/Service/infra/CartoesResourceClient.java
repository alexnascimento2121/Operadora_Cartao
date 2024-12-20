package com.github.alexnascimento2121.msAvaliadorCredito.Service.infra;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.alexnascimento2121.msAvaliadorCredito.Model.Cartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.CartaoCliente;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {
	 	@GetMapping(params = "cpf")
	    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

	    @GetMapping(params = "renda")
	    ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);

}
