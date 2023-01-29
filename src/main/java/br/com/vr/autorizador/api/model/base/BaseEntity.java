package br.com.vr.autorizador.api.model.base;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", precision = 19, scale = 0, nullable = false, updatable = false)
	private Long id;

	@Column(name = "DH_CRIACAO", nullable = false, updatable = false)
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "DH_ALTERACAO", nullable = true, updatable = true)
	private LocalDateTime dataAlteracao;

}