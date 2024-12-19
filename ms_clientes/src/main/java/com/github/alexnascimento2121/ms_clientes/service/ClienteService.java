package com.github.alexnascimento2121.ms_clientes.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.alexnascimento2121.ms_clientes.infra.repository.ClienteRepository;
import com.github.alexnascimento2121.ms_clientes.model.Cliente;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }
    // pode ser ou nao q exista o client para cpf que esta sendo usado.
    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
