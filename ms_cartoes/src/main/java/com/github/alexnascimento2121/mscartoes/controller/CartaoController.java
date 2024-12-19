package com.github.alexnascimento2121.mscartoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.alexnascimento2121.mscartoes.controller.request.CartaoSaveRequest;
import com.github.alexnascimento2121.mscartoes.controller.request.CartoesPorClienteResponse;
import com.github.alexnascimento2121.mscartoes.model.Cartao;
import com.github.alexnascimento2121.mscartoes.model.ClienteCartao;
import com.github.alexnascimento2121.mscartoes.service.CartaoService;
import com.github.alexnascimento2121.mscartoes.service.ClienteCartaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class CartaoController {
	
	private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;
	
	@GetMapping
    public String status(){
        return "ok";
    }
	
	 
	@PostMapping
	    public ResponseEntity cadastra( @RequestBody CartaoSaveRequest request ){
	        Cartao cartao = request.toModel();
	        cartaoService.save(cartao);
	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    }

	    @GetMapping(params = "renda")
	    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
	        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
	        return ResponseEntity.ok(list);
	    }

	    @GetMapping(params = "cpf")
	    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(
	            @RequestParam("cpf") String cpf){
	        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
	        List<CartoesPorClienteResponse> resultList = lista.stream()
	                .map(CartoesPorClienteResponse::fromModel)
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(resultList);
	    }

}
