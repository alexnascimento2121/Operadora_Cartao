package com.github.alexnascimento2121.msAvaliadorCredito.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.DadosClienteNotFoundException;
import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.ErroComunicacaoMicroservicesException;
import com.github.alexnascimento2121.msAvaliadorCredito.Controller.exception.ErroSolicitacaoCartaoException;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.DadosAvaliacao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.DadosSolicitacaoEmissaoCartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.ProtocoloSolicitacaoCartao;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.RetornoAvaliacaoCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Model.SituacaoCliente;
import com.github.alexnascimento2121.msAvaliadorCredito.Service.AvaliadorCreditoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class AvaliacoesCreditoController {
	
	private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    
	@GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf){
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao( @RequestBody DadosAvaliacao dados ){
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
                    .realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
        try{
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService
                    .solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        }catch (ErroSolicitacaoCartaoException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
