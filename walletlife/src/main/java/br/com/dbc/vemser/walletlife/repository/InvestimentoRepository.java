package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.modelos.Investimento;
import br.com.dbc.vemser.walletlife.modelos.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {
    List<Investimento> listInvestimentoListByIdFK(Integer idFk);
}
