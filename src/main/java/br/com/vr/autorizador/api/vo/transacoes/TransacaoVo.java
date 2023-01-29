package br.com.vr.autorizador.api.vo.transacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoVo {

	@Schema(description = "Informe o número do cartão", defaultValue = "6549873025634501")
	@NotNull(message = "{transacaoVo.numeroCartao.notnull}")
	@NotEmpty(message = "{transacaoVo.numeroCartao.notempty}")
	@Size(message = "{transacaoVo.numeroCartao.size}", min = 16, max = 16)
	private String numeroCartao;

	@Schema(description = "Informe a senha do cartão", defaultValue = "1234")
	@NotNull(message = "{transacaoVo.senha.notnull}")
	@NotEmpty(message = "{transacaoVo.senha.notempty}")
	@Size(message = "{transacaoVo.senha.size}", min = 4, max = 4)
	private String senha;

	@Schema(description = "Informe o valor da transação", defaultValue = "0.01")
	@NotNull(message = "{transacaoVo.valor.notnull}")
	@DecimalMin(message = "{transacaoVo.valor.min}", value = "0.01")
	private BigDecimal valor;

}