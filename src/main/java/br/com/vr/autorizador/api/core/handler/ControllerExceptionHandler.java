package br.com.vr.autorizador.api.core.handler;

import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.core.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.api.core.exception.SenhaInvalidaException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiErrorResponse handleSpringBindException(final BindException ex) {
		log.error("handleSpringBindException: {}", ex.getMessage(), ex);
		final List<ApiErrorData> errorsData = ex.getFieldErrors().stream().map(error -> ApiErrorData.builder().field(error.getField()).type("Invalid Parameter").message(error.getDefaultMessage()).build()).collect(Collectors.toList());
		return new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorsData, ex.getLocalizedMessage());
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ApiErrorResponse handleConstraintViolationException(final ConstraintViolationException ex) {
		log.error("handleConstraintViolationException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getLocalizedMessage());
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrorResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
		log.error("handleBadCredentialsException: {}", ex.getMessage(), ex);
		final List<ApiErrorData> errorsData = ex.getBindingResult().getFieldErrors().stream().map(error -> ApiErrorData.builder().field(error.getField()).type("Invalid Parameter").message(error.getDefaultMessage()).build()).collect(Collectors.toList());
		return new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorsData, ex.getLocalizedMessage());
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ApiErrorResponse handleNotFoundException(final NotFoundException ex) {
		log.error("handleBadCredentialsException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getLocalizedMessage());
	}

	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(CartaoNaoExisteException.class)
	public ApiErrorResponse handleCartaoNaoExisteException(final CartaoNaoExisteException ex) {
		log.error("CartaoNaoExisteException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "O número de cartão informado não existe.", null);
	}

	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(SaldoInsuficienteException.class)
	public ApiErrorResponse handleSaldoInsuficienteException(final SaldoInsuficienteException ex) {
		log.error("handleSaldoInsuficienteException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Saldo insuficiente para realizar a transação.", null);
	}

	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(SenhaInvalidaException.class)
	public ApiErrorResponse handleSenhaInvalidaException(final SenhaInvalidaException ex) {
		log.error("handleSenhaInvalidaException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "A senha do cartão está inválida.", null);
	}

	@ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiErrorResponse handleException(final Exception ex) {
		log.error("handleException: {}", ex.getMessage(), ex);
		return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado, contate o administrador do sistema!", ex.getLocalizedMessage());
	}

}
