package br.com.dbc.vemser.walletlife.dto;

import br.com.dbc.vemser.walletlife.modelos.Receita;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ReceitaDTO extends ReceitaCreateDTO {
    private Integer id;

    public ReceitaDTO(Receita entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public ReceitaDTO(@NotNull(message = "O valor não pode ser nulo") Double valor, @NotNull(message = "A descrição não pode ser nula") @Size(min = 5, max = 30, message = "A descrição deve ter entre 5 e 30 caracteres") String descricao, @NotEmpty(message = "O banco não pode estar vazio") String banco, @NotEmpty(message = "A empresa não pode estar vazia") String empresa, Integer id) {
        super(valor, descricao, banco, empresa);
        this.id = id;
    }
}
