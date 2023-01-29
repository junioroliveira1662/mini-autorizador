package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoJaExisteException;
import br.com.vr.autorizador.api.model.CartaoEntity;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import br.com.vr.autorizador.api.vo.cartoes.CartaoVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
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
public class CadastrarCartaoServiceTest {

    @Autowired(required = true)
    private CadastrarCartaoService cadastrarCartaoService;

    @Autowired
    private ICartaoRepository cartaoRepository;

    void inicializar() {
        var cartao = CartaoEntity.builder().numero("1111222233334444").senha("1234").saldo(new BigDecimal("200.0")).saldoBloqueado(BigDecimal.ZERO).build();
        cartaoRepository.saveAndFlush(cartao);
    }

    @Test
    public void cadastrarNovoCartao_RetornoSucesso(){
        inicializar();

        var cartao = CartaoVo.builder().numeroCartao("1234432112344321").senha("1234").build();

        var cartaoCriado = cadastrarCartaoService.novo(cartao);

        var cartaoResultado = cartaoRepository.findByNumero(cartaoCriado.getNumeroCartao());

        assertEquals(new BigDecimal(500), cartaoResultado.getSaldo());
    }

    @Test(expected = CartaoJaExisteException.class)
    public void cadastrarNovoCartao_RetornoErro_CartaoJaExiste(){
        inicializar();

        var cartao = CartaoVo.builder().numeroCartao("1111222233334444").senha("1234").build();

        cadastrarCartaoService.novo(cartao);
    }

}