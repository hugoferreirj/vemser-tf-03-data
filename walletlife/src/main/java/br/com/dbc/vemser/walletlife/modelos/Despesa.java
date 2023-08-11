package br.com.dbc.vemser.walletlife.modelos;

import br.com.dbc.vemser.walletlife.enumerators.TipoDespesaEReceita;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DESPESA")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESPESA_SEQUENCE")
    @SequenceGenerator(name = "DESPESA_SEQUENCE", sequenceName = "seq_despesa", allocationSize = 1)
    @Column(name = "id_despesa")
    @NotNull
    private Integer idDespesa;

    @Column(name = "tipo")
    private TipoDespesaEReceita tipo;

    @NotEmpty
    @Schema(description = "Data do pagamento", required = true)
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull
    @Schema(description = "ID de referência associado a despesa", required = true)
    @Column(name = "idFK")
    private int idFK;

    @NotNull
    @Schema(description = "Valor da despesa", required = true)
    @Column(name = "valor")
    private Double valor;

    @NotNull
    @Schema(description = "Descricao da despesa", required = true)
    @Size(min = 5, max = 30)
    @Column(name = "descricao")
    private String descricao;
}