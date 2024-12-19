package com.github.alexnascimento2121.mscartoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.alexnascimento2121.mscartoes.model.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
