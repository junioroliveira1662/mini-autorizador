package br.com.vr.autorizador.api.service;

import br.com.vr.autorizador.api.core.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.api.model.CartaoEntity;
import br.com.vr.autorizador.api.repository.ICartaoRepository;
import br.com.vr.autorizador.api.vo.cartoes.CartaoVo;
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
public class SaldoCartaoServiceTest {

    @Autowired(required = true)
    private SaldoCartaoService saldoCartaoService;

    @Autowired
    private ICartaoRepository cartaoRepository;

    private void inicializar(){
        var cartao = CartaoEntity.builder().numero("1111222233334444").senha("1234").saldo(new BigDecimal("200.0")).saldoBloqueado(BigDecimal.ZERO).build();
        cartaoRepository.saveAndFlush(cartao);
    }

    @Test
    public void consultaSaldoCartao_RetornoSucesso(){
        inicializar();

        var saldo = saldoCartaoService.consultar("1111222233334444");

        assertEquals(new BigDecimal("200.00"), saldo);
    }

    @Test(expected = CartaoNaoExisteException.class)
    public void consultaSaldoCartao_RetornoErro_CartaoNaoExiste(){
        inicializar();

        var cartao = CartaoVo.builder().numeroCartao("1234432112344321").senha("1234").build();

        saldoCartaoService.consultar("0000000000000000");
    }

}