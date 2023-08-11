package br.com.dbc.vemser.walletlife.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RECEITA")
public class ReceitaEntity extends AbstractMovimentoDinheiro {
    @NotEmpty
    @Column(name = "banco")
    private String banco;

    @NotEmpty
    @Column(name = "empresa")
    private String empresa;

    @Column(name = "id_usuario")
    private Integer idFK;
}
