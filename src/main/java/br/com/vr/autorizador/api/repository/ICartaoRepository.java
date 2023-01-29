package br.com.vr.autorizador.api.repository;

import br.com.vr.autorizador.api.model.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartaoRepository extends JpaRepository<CartaoEntity, Long> {

    CartaoEntity findByNumero(final String numero);

}
