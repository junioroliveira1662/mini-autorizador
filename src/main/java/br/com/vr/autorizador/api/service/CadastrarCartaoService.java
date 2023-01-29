package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoJaExisteException;
import br.com.vr.autorizador.api.core.util.UtilMapper;
import br.com.vr.autorizador.api.model.CartaoEntity;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import br.com.vr.autorizador.api.vo.cartoes.CartaoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Log4j2
@Service
public class CadastrarCartaoService {

    @Autowired
    private ICartaoRepository cartaoRepository;

    /**
     * @return Retorna os dados do cartão cadastrado.
     * @param: body - Informar os dados do novo cartão a ser cadastrado
     * @since 2023-01-28
     * <p>
     * Realiza o cadastro de um novo cartão
     */
    public CartaoVo novo(final CartaoVo body) {
        log.debug("BEGIN novo: numeroCartao={}", body.getNumeroCartao());

        var teste = cartaoRepository.findByNumero(body.getNumeroCartao());

        Optional.ofNullable(cartaoRepository.findByNumero(body.getNumeroCartao())).ifPresent(e -> {
            throw new CartaoJaExisteException();
        });

        var entity = UtilMapper.merge(body, CartaoEntity.class);

        var response = cartaoRepository.saveAndFlush(entity);

        log.debug("END novo.");

        return UtilMapper.merge(response, CartaoVo.class);
    }

}