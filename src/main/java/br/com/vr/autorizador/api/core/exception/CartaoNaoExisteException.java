package br.com.vr.autorizador.api.core.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "O número de cartão informado não existe.")
public class CartaoNaoExisteException extends RuntimeException {

}