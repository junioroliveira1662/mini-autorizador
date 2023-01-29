package br.com.vr.autorizador.api.controller;

import br.com.vr.autorizador.api.core.exception.CartaoJaExisteException;
import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.core.handler.ApiErrorResponse;
import br.com.vr.autorizador.api.service.CadastrarCartaoService;
import br.com.vr.autorizador.api.service.SaldoCartaoService;
import br.com.vr.autorizador.api.vo.cartoes.CartaoVo;
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
@RequestMapping(path = "cartoes", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CartoesController {

    @Autowired
    private CadastrarCartaoService cadastrarCartaoService;

    @Autowired
    private SaldoCartaoService saldoCartaoService;

    @Operation(summary = "Consulta de operações disponíveis", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal Server Erro", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> saldo(@NotNull(message = "{cartoesController.saldo.numeroCartao.notnull}")
                                            @NotEmpty(message = "{cartoesController.saldo.numeroCartao.notempty}")
                                            @Size(min = 16, max = 16, message = "{cartoesController.saldo.numeroCartao.size}")
                                            @Parameter(required = true, description = "Informe o número do cartão", example = "6549873025634501")
                                            @PathVariable final String numeroCartao) {
        log.debug("BEGIN saldo");

        try {
            var response = this.saldoCartaoService.consultar(numeroCartao);

            log.debug("END saldo: response: {}", response);

            return new ResponseEntity<BigDecimal>(response, HttpStatus.OK);

        } catch (CartaoNaoExisteException ex) {
            log.error("Cartão não existe: numeroCartao={}", numeroCartao);
            return new ResponseEntity<BigDecimal>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Realizar o cadastro de um novo cartão", responses = {
            @ApiResponse(description = "Cartão criado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoVo.class))),
            @ApiResponse(responseCode = "400", description = "Falha ao criar o novo cartão", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Cartão já existente", content = @Content(schema = @Schema(implementation = CartaoVo.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CartaoVo> cadastrarNovoCartao(@RequestBody @Valid final CartaoVo body) {
        log.debug("BEGIN cadastrarNovoCartao: numeroCartao={}", body.getNumeroCartao());

        try {
            var response = this.cadastrarCartaoService.novo(body);

            log.debug("END cadastrarNovoCartao: response: {}", response);

            return new ResponseEntity<CartaoVo>(response, HttpStatus.CREATED);

        } catch (CartaoJaExisteException ex) {
            log.error("Cartão já existe: numeroCartao={}", body.getNumeroCartao());
            return new ResponseEntity<CartaoVo>(body, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}