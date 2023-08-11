package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
