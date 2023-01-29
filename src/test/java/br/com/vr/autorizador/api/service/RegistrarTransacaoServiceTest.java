package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.core.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.api.core.exception.SenhaInvalidaException;
import br.com.vr.autorizador.api.model.CartaoEntity;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import br.com.vr.autorizador.api.vo.transacoes.TransacaoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RegistrarTransacaoServiceTest {

    @Autowired(required = true)
    private RegistrarTransacaoService registrarTransacaoService;

    @Autowired
    private ICartaoRepository cartaoRepository;

    private void inicializar(){
        var cartao = CartaoEntity.builder().numero("1111222233334444").senha("1234").saldo(new BigDecimal("200.0")).saldoBloqueado(BigDecimal.ZERO).build();
        cartaoRepository.saveAndFlush(cartao);
    }

    @Test
    public void registrarTransacao_RetornoSucesso(){
        inicializar();

        var saldoInicial = cartaoRepository.findByNumero("1111222233334444").getSaldo();

        var transacao = TransacaoVo.builder().numeroCartao("1111222233334444").senha("1234").valor(new BigDecimal("15.0")).build();

        var resultado = registrarTransacaoService.registrar(transacao);

        var saldoAtualizado = cartaoRepository.findByNumero("1111222233334444").getSaldo();

        assertEquals("OK", resultado);
        assertTrue(saldoAtualizado.compareTo(saldoInicial.subtract(transacao.getValor())) == 0);
    }

    @Test(expected = CartaoNaoExisteException.class)
    public void registrarTransacao_RetornoErro_CartaoNaoExiste(){
        inicializar();

        var transacao = TransacaoVo.builder().numeroCartao("0000000000000000").senha("1234").valor(new BigDecimal("15.0")).build();

        registrarTransacaoService.registrar(transacao);
    }

    @Test(expected = SenhaInvalidaException.class)
    public void registrarTransacao_RetornoErro_SenhaInvalida(){
        inicializar();

        var transacao = TransacaoVo.builder().numeroCartao("1111222233334444").senha("0000").valor(new BigDecimal("15.0")).build();

        registrarTransacaoService.registrar(transacao);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void registrarTransacao_RetornoErro_SaldoInsuficiente(){
        inicializar();

        var transacao = TransacaoVo.builder().numeroCartao("1111222233334444").senha("1234").valor(new BigDecimal("201.0")).build();

        registrarTransacaoService.registrar(transacao);
    }

}