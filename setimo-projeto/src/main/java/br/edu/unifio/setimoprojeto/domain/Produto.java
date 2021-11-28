package br.edu.unifio.setimoprojeto.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo nome é necessário")
    @Size(min = 2, max = 50, message = "O tamanho do campo nome deve ser entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "O campo preço é obrigatório")
    @DecimalMin(value = "0.01", message = "o valor minimo é 0")
    @DecimalMax(value = "1000.00", message = "o valor máximo para esse campo é 1000.00")
    private  Double preco;

    @NotNull(message = "O campo quantidade é obrigatório")
    @Min(value = 0, message = "O valor minimo para o campo quantidade é zero")
    @Max(value = 10, message = "o valor máximo para o campo é 10")
    private Integer quantidade;

    @NotNull(message = "O campo validade é necessário")
    @FutureOrPresent(message = "A data informada no campo validade deve ser uma data presente ou futura")
    private LocalDate validade;



}
