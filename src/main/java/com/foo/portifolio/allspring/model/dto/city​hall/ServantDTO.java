package com.foo.portifolio.allspring.model.dto.city​hall;

import java.time.LocalDate;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;
import com.foo.portifolio.allspring.utils.AgeBetween;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServantDTO extends BaseEntityDTO<Integer> {
	@NotBlank(message = "O nome é obrigatório")
	private String name;

	@Email(message = "E-mail inválido")
	@NotBlank(message = "O e-mail é obrigatório")
	private String email;
	
	@NotNull(message = "A data de nascimento é obrigatória")
	@Past(message = "A data de nascimento deve estar no passado")
	@AgeBetween(min = 18, max = 75, message = "A idade deve estar entre 18 e 75 anos")
	private LocalDate birthday;
	
	@NotNull(message = "A secretaria vinculada é obrigatória")
	private SecretariatDTO secretariat;

}
