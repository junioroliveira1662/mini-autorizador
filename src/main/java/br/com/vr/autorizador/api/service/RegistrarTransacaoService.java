package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.core.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.api.core.exception.SenhaInvalidaException;
import br.com.vr.autorizador.api.model.CartaoEntity;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import br.com.vr.autorizador.api.vo.transacoes.TransacaoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
public class RegistrarTransacaoService {

    @Autowired
    private ICartaoRepository cartaoRepository;

    /**
     * @param: body - Informar os dados da nova transação a ser registrada
     * @since 2023-01-28
     * <p>
     * Registra e valida uma nova transação
     */
    public String registrar(final TransacaoVo body) {
        log.debug("BEGIN registrar: numeroCartao={}", body.getNumeroCartao());

        var cartaoEntity = Optional.ofNullable(cartaoRepository.findByNumero(body.getNumeroCartao())).orElseThrow(()-> new CartaoNaoExisteException());

        registrarBloqueioSaldo(cartaoEntity, body.getValor(), false);

        try {
            Optional.ofNullable(cartaoEntity.getSenha().equals(body.getSenha()) ? "OK" : null).orElseThrow(() -> new SenhaInvalidaException());

            var saldoAtual = cartaoEntity.getSaldo().subtract(cartaoEntity.getSaldoBloqueado());

            Optional.ofNullable(saldoAtual.compareTo(BigDecimal.ZERO) >= 0 ? "OK" : null).orElseThrow(() -> new SaldoInsuficienteException());

            registrarSaldo(cartaoEntity, body.getValor());

        } catch (SenhaInvalidaException ex) {
            registrarBloqueioSaldo(cartaoEntity, body.getValor(), true);
            throw new SenhaInvalidaException();
        } catch (SaldoInsuficienteException ex) {
            registrarBloqueioSaldo(cartaoEntity, body.getValor(), true);
            throw new SaldoInsuficienteException();
        }

        log.debug("END registrar.");

        return "OK";
    }

    private void registrarBloqueioSaldo(final CartaoEntity cartaoEntity, final BigDecimal valor, final boolean desbloquear){
        log.debug("BEGIN registrarBloqueioSaldo: numeroCartao={} | valor={}", cartaoEntity.getNumero(), valor);

        var saldoBloqueado = desbloquear ? cartaoEntity.getSaldoBloqueado().subtract(valor) : cartaoEntity.getSaldoBloqueado().add(valor);

        cartaoEntity.setSaldoBloqueado(saldoBloqueado);
        cartaoEntity.setDataAlteracao(LocalDateTime.now());
        cartaoRepository.saveAndFlush(cartaoEntity);

        log.debug("END registrarBloqueioSaldo.");
    }

    private void registrarSaldo(final CartaoEntity cartaoEntity, final BigDecimal valor){
        log.debug("BEGIN registrarSaldo: numeroCartao={}", cartaoEntity.getNumero());

        cartaoEntity.setSaldoBloqueado(cartaoEntity.getSaldoBloqueado().subtract(valor));
        cartaoEntity.setDataAlteracao(LocalDateTime.now());
        cartaoEntity.setSaldo(cartaoEntity.getSaldo().subtract(valor));
        cartaoRepository.saveAndFlush(cartaoEntity);

        log.debug("END registrarSaldo.");
    }

}