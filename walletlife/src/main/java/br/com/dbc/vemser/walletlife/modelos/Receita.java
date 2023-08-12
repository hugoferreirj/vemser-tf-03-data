package br.com.dbc.vemser.walletlife.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RECEITA")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEITA_SEQ")
    @SequenceGenerator(name = "RECEITA_SEQ", sequenceName = "SEQ_RECEITA", allocationSize = 1)
    @Column(name = "id_receita")
    private Integer id;

    @NotNull
    @Column(name = "valor")
    private Double valor;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(name = "descricao")
    private String descricao;

    @NotEmpty
    @Column(name = "banco")
    private String banco;

    @NotEmpty
    @Column(name = "empresa")
    private String empresa;

//    @Column(name = "id_usuario")
//    private Integer idFK;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
}
