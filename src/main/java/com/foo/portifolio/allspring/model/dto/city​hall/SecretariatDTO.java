package com.foo.portifolio.allspring.model.dto.city​hall;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;
import lombok.Data;

@Data
public class SecretariatDTO extends BaseEntityDTO<Integer> {
    private String name;

    private String acronym;
}
