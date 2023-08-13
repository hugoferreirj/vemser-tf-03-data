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
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INVESTIMENTO")
public class Investimento extends AbstractMovimentoDinheiro{

    @Id
    private Integer idInvestimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDespesaEReceita tipo;

    @NotEmpty
    @Schema(description = "Nome da corretora do investimento", required = true)
    @Column(name = "corretora")
    protected String corretora;

    @NotNull
    @Schema(description = "Data de início do investimento", required = true)
    @Column(name = "DATA_INICIAL")
    private LocalDate dataInicio;

//    @NotNull
//    @Schema(description = "ID de referência associado ao investimento", required = true)
//    @Column(name = "idFK")
//    private int idFK;

    public Investimento(TipoDespesaEReceita tipo, Double valor, String descricao) {
    }
}
