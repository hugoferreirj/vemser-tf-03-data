package br.com.dbc.vemser.walletlife.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComDespesaDTO {
    private Integer idUsuario;
    private String nome;
    private Integer idDespesa;
    private Double valor;
    private  String descricao;

    public UsuarioComDespesaDTO(Integer idUsuario, String nome, Integer idDespesa, Double valor, String descricao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idDespesa = idDespesa;
        this.valor = valor;
        this.descricao = descricao;
    }
}

