package br.com.dbc.vemser.walletlife.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComReceitaDTO {

    private Integer idUsuario;
    private String nome;
    private Integer idReceita;
    private Double valor;
    private  String descricao;
    private String banco;

    public UsuarioComReceitaDTO(Integer idUsuario, String nome, Integer idReceita, Double valor, String descricao, String banco) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idReceita = idReceita;
        this.valor = valor;
        this.descricao = descricao;
        this.banco = banco;
    }
}
