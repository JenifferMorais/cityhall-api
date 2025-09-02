package com.foo.cityhall.controller.cityhall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.cityhall.controller.city​hall.ServantController;
import com.foo.cityhall.model.dto.city​hall.ServantDTO;
import com.foo.cityhall.model.entity.city​hall.Servant;
import com.foo.cityhall.service.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ServantController.class)
@AutoConfigureMockMvc(addFilters = false)
class ServantControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean BaseService<Servant, ServantDTO, Integer> service;

    @MockBean Validator validator;

    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    void stubValidator() {
        when(validator.validate(any(Object.class))).thenReturn(Collections.<ConstraintViolation<Object>>emptySet());
    }

    @Test
    void index_ReturnsPage() throws Exception {
        ServantDTO s1 = new ServantDTO(); s1.setId(1);
        ServantDTO s2 = new ServantDTO(); s2.setId(2);
        var page = new PageImpl<>(List.of(s1, s2), PageRequest.of(0, 2), 2);

        when(service.index(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/servants").param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void read_ReturnsDto() throws Exception {
        ServantDTO dto = new ServantDTO(); dto.setId(10);
        when(service.read(10)).thenReturn(dto);

        mockMvc.perform(get("/servants/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void create_ReturnsPersisted() throws Exception {
        ServantDTO input = new ServantDTO();
        ServantDTO persisted = new ServantDTO(); persisted.setId(100);

        when(service.create(any(ServantDTO.class))).thenReturn(persisted);

        mockMvc.perform(post("/servants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(persisted)));
    }

    @Test
    void update_ReturnsUpdated() throws Exception {
        ServantDTO input = new ServantDTO(); input.setId(5);
        ServantDTO updated = new ServantDTO(); updated.setId(5);

        when(service.update(any(ServantDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/servants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    void delete_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/servants/7"))
                .andExpect(status().isOk());
    }
}
