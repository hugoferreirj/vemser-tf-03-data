package br.com.dbc.vemser.walletlife.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public abstract class AbstractMovimentoDinheiro {

    @NotNull
    private Double valor;

    @NotNull
    @Size(min = 5, max = 30)
    private String descricao;

    public AbstractMovimentoDinheiro(Double valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }
}
