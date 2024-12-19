package com.github.alexnascimento2121.mscartoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.alexnascimento2121.mscartoes.model.ClienteCartao;
import com.github.alexnascimento2121.mscartoes.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
