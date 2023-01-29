package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Log4j2
@Service
public class SaldoCartaoService {

    @Autowired
    private ICartaoRepository cartaoRepository;

    /**
     * @return Retorna o saldo disponível do cartão.
     * @param: numeroCartao - Informar o número do cartão que deseja consultar o saldo
     * @since 2023-01-28
     * <p>
     * Realiza a consulta de saldo do cartão
     */
    public BigDecimal consultar(final String numeroCartao) {

        log.debug("BEGIN consultar: numeroCartao={}", numeroCartao);

        var entity = Optional.ofNullable(cartaoRepository.findByNumero(numeroCartao)).orElseThrow(()-> new CartaoNaoExisteException());

        log.debug("END consultar.");

        return entity.getSaldo();
    }

}