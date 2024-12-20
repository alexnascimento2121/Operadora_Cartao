package com.github.alexnascimento2121.msAvaliadorCredito.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.DadosClienteNotFoundException;
import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.ErroComunicacaoMicroservicesException;
import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.ErroSolicitacaoCartaoException;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.Cartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.CartaoAprovado;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.CartaoCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.DadosCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.DadosSolicitacaoEmissaoCartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.ProtocoloSolicitacaoCartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.RetornoAvaliacaoCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.SituacaoCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Service.infra.CartoesResourceClient;
import com.github.alexnascimento2121.msAvaliadorCredito.Service.infra.ClienteResourceClient;
import com.github.alexnascimento2121.msAvaliadorCredito.Service.infra.SolicitacaoEmissaoCartaoPublisher;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try{
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
