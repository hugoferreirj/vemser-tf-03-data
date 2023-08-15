package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.dto.UsuarioComDespesaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioComReceitaDTO;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("""
        SELECT NEW br.com.dbc.vemser.walletlife.dto.UsuarioComDespesaDTO(u.idUsuario, u.nome, d.idDespesa, d.valor, d.descricao)
        FROM Usuario u
        JOIN u.despesas d
        where d.usuario.idUsuario = u.idUsuario
    """)
    Set<UsuarioComDespesaDTO> findAllUsuariosDespesa();

//    @Query("""
//        SELECT NEW br.com.dbc.vemser.walletlife.dto.UsuarioComDespesaDTO(u.idUsuario, u.nome, d.idDespesa, d.valor, d.descricao)
//        FROM Usuario u
//        JOIN u.despesas d
//        where d.usuario.idUsuario = u.idUsuario
//    """)
//    Set<UsuarioComDespesaDTO> findallUsuarioReceita();

    @Query("""
        SELECT new br.com.dbc.vemser.walletlife.dto.UsuarioComReceitaDTO
        (u.idUsuario, u.nome, r.id, r.valor, r.descricao, r.banco)
        FROM Usuario u
        JOIN u.receitas r
        WHERE (:valor is null or r.valor = :valor)
    """)
    Set<UsuarioComReceitaDTO> findallUsuarioReceita(@Param("valor") Double valor);
}
