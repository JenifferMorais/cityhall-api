package com.foo.cityhall.model.dto.city​hall;

import com.foo.cityhall.model.dto.BaseEntityDTO;

import lombok.Data;

@Data
public class SecretariatDTO extends BaseEntityDTO<Integer> {
    private String name;

    private String acronym;
}
