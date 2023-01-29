package br.com.vr.autorizador.api.controller;

import br.com.vr.autorizador.api.core.exception.CartaoJaExisteException;
import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.core.handler.ApiErrorResponse;
import br.com.vr.autorizador.api.service.CadastrarCartaoService;
import br.com.vr.autorizador.api.service.RegistrarTransacaoService;
import br.com.vr.autorizador.api.service.SaldoCartaoService;
import br.com.vr.autorizador.api.vo.cartoes.CartaoVo;
import br.com.vr.autorizador.api.vo.transacoes.TransacaoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Log4j2
@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "transacoes", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TransacoesController {

    @Autowired
    private RegistrarTransacaoService registrarTransacaoService;

    @Operation(summary = "Registrar uma nova transação", responses = {
            @ApiResponse(description = "Transação registrada com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Falha ao registrar a transação", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Falha na regra de autorização", content = @Content(schema = @Schema(implementation = CartaoVo.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<String> registrarTransacao(@RequestBody @Valid final TransacaoVo body) {
        log.debug("BEGIN registrarTransacao: numeroCartao={}", body.getNumeroCartao());

        var response = this.registrarTransacaoService.registrar(body);

        log.debug("END registrarTransacao: response={}", response);

        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

}