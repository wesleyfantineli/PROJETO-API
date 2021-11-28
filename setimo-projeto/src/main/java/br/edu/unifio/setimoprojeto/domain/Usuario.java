package br.edu.unifio.setimoprojeto.domain;


import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Usuario{
    private Integer USR_ID;

    @NotBlank(message = "O campo e-mail é obrigatório!")
    @Size(min = 2, max = 50, message = "O tamanho do campo e-mail deve ser entre 3 e 50 caracteres")
    private String USR_EMAIL;

    @NotBlank(message = "O campo senha é obrigatório!")
    @Size(min = 2, max = 50, message = "O tamanho do campo senha deve ser entre 3 e 50 caracteres")
    private String USR_SENHA;


}



















