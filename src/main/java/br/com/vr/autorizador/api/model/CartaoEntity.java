package br.com.vr.autorizador.api.model;

import br.com.vr.autorizador.api.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CARTAO")
public class CartaoEntity extends BaseEntity {

	@Column(name = "NUMERO", length = 16, unique = true, nullable = false)
	private String numero;

	@Column(name = "SENHA", length = 4, nullable = false)
	private String senha;

	@Column(name = "SALDO", precision = 19, scale = 2, nullable = false)
	private BigDecimal saldo = new BigDecimal(500);

	@Column(name = "SALDO_BLOQUEADO", precision = 19, scale = 2, nullable = false)
	private BigDecimal saldoBloqueado = BigDecimal.ZERO;

}