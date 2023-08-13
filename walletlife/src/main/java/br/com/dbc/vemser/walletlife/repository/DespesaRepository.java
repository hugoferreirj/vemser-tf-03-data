package br.com.dbc.vemser.walletlife.repository;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.modelos.Despesa;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa,Integer> {
//    List<Despesa> listDespesaListByIdFK(Integer idFk);
}