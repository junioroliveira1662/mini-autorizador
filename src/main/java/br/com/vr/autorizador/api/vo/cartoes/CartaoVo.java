package br.com.vr.autorizador.api.vo.cartoes;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoVo {

	@Schema(description = "Informe o número do cartão", defaultValue = "6549873025634501")
	@NotNull(message = "{cartaoVo.numeroCartao.notnull}")
	@NotEmpty(message = "{cartaoVo.numeroCartao.notempty}")
	@Size(message = "{cartaoVo.numeroCartao.size}", min = 16, max = 16)
	private String numeroCartao;

	@Schema(description = "Informe a senha do cartão", defaultValue = "1234")
	@NotNull(message = "{cartaoVo.senha.notnull}")
	@NotEmpty(message = "{cartaoVo.senha.notempty}")
	@Size(message = "{cartaoVo.senha.size}", min = 4, max = 4)
	private String senha;

}