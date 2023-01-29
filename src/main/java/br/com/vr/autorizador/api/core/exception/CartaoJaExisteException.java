package br.com.vr.autorizador.api.core.exception;

import br.com.vr.autorizador.api.core.handler.ApiErrorData;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "O número de cartão informado já existe.")
public class CartaoJaExisteException extends RuntimeException {

}