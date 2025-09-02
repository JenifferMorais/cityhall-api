package com.foo.portifolio.allspring.model.mapper;

import org.springframework.stereotype.Component;
import com.foo.portifolio.allspring.model.dto.auth.AuthDTO;
import com.foo.portifolio.allspring.model.entity.user.User;

@Component
public class AuthMapper implements BaseMapper<User, AuthDTO> {

    @Override
    public User toEntity(AuthDTO dto) {
        if (dto == null) return null;
        User e = new User();
        e.setId(dto.getId());
        e.setUsername(dto.getUsername());
        e.setPassword(dto.getPassword());
        return e;
    }

    @Override
    public AuthDTO toDto(User entity) {
        if (entity == null) return null;
        AuthDTO dto = new AuthDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public void update(User entity, AuthDTO dto) {
        if (dto == null) return;
        if (dto.getUsername() != null) entity.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) entity.setPassword(dto.getPassword());
    }
}
