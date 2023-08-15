package br.com.dbc.vemser.walletlife.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComInvestimentoDTO {
    private Integer idUsuario;
    private String nome;
    private Integer idInvestimento;
    private Double valor;
    private String corretora;

    public UsuarioComInvestimentoDTO(Integer idUsuario,
                                     String nome,
                                     Integer idInvestimento,
                                     Double valor,
                                     String corretora) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.idInvestimento = idInvestimento;
        this.valor = valor;
        this.corretora = corretora;
    }
}
