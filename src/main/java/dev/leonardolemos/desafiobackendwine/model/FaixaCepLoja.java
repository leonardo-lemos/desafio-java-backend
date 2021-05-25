package dev.leonardolemos.desafiobackendwine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_faixas_cep_loja")
@AttributeOverride(name = "id", column = @Column(name = "id"))
@SequenceGenerator(allocationSize = 1, name = "default_id_generator", sequenceName = "seq_faixas_cep_loja")
public @Entity
class FaixaCepLoja extends BaseEntity {

    @NotEmpty(message = "O campo [codigo_loja] não pode ser vazio")
    @Column(name = "codigo_loja", length = 128, nullable = false)
    @JsonProperty("codigo_loja")
    private String codigoLoja;

    @NotNull(message = "O campo [faixa_inicio] não pode ser vazio")
    @Column(name = "faixa_inicio", length = 8, nullable = false)
    @JsonProperty("faixa_inicio")
    private Long inicioFaixaCep;

    @NotNull(message = "O campo [faixa_fim] não pode ser vazio")
    @Column(name = "faixa_fim", length = 8, nullable = false)
    @JsonProperty("faixa_fim")
    private Long fimFaixaCep;

    @JsonIgnore
    @AssertTrue(message = "O campo [faixa_inicio] precisa conter 8 dígitos")
    public boolean isFaixaInicioValid() {
        if (inicioFaixaCep != null) {
            return inicioFaixaCep.toString().length() >= 8;
        }

        return false;
    }

    @JsonIgnore
    @AssertTrue(message = "O campo [faixa_fim] precisa conter 8 dígitos")
    public boolean isFaixaFimValid() {
        if (fimFaixaCep != null) {
            return fimFaixaCep.toString().length() >= 8;
        }

        return false;
    }

}
