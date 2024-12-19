package com.github.alexnascimento2121.mscartoes.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.alexnascimento2121.mscartoes.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	List<Cartao> findByRendaLessThanEqual(BigDecimal rendaBigDecimal);

}
