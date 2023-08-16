package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.dto.ReceitaDTO;
import br.com.dbc.vemser.walletlife.modelos.Receita;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Integer> {

    public List<Receita> findByUsuario(Usuario usuario);
    @Query("Select new br.com.dbc.vemser.walletlife.dto.ReceitaDTO(r) From RECEITA r")
    public Page<ReceitaDTO> findAllPages(Pageable pageable);

}
