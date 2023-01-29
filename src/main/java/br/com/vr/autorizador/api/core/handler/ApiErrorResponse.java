package br.com.vr.autorizador.api.core.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

	private String createdAt;
	private int statusCode;
	private String statusDescription;
	private String message;
	private List<ApiErrorData> errors;
	private Object trace;

	public ApiErrorResponse(HttpStatus status, List<ApiErrorData> errors, String trace) {
		this.statusCode = status.value();
		this.statusDescription = status.getReasonPhrase();
		this.errors = errors;
		this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.message = errors.size() == 1 ? errors.get(0).getMessage() : "Details errors";
		this.trace = trace;
	}

	public ApiErrorResponse(HttpStatus status, String message, String trace) {
		final ApiErrorData errorData = ApiErrorData.builder().type(status.getReasonPhrase()).message(message).build();
		this.statusCode = status.value();
		this.statusDescription = status.getReasonPhrase();
		this.errors = Collections.unmodifiableList(Arrays.asList(errorData));
		this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.message = message;
		this.trace = trace;
	}

}
