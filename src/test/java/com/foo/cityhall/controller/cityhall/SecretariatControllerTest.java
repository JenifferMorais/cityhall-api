package com.foo.cityhall.controller.cityhall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.cityhall.controller.city​hall.SecretariatController;
import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;
import com.foo.cityhall.service.BaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SecretariatController.class)
@AutoConfigureMockMvc(addFilters = false)
class SecretariatControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean BaseService<Secretariat, SecretariatDTO, Integer> service;
    @Autowired ObjectMapper objectMapper;

    @Test
    void index_ReturnsPage() throws Exception {
        SecretariatDTO s1 = new SecretariatDTO(); s1.setId(1);
        SecretariatDTO s2 = new SecretariatDTO(); s2.setId(2);
        var page = new PageImpl<>(List.of(s1, s2), PageRequest.of(0, 2), 2);
        when(service.index(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/secretariats").param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void read_ReturnsDto() throws Exception {
        SecretariatDTO dto = new SecretariatDTO(); dto.setId(10);
        when(service.read(10)).thenReturn(dto);

        mockMvc.perform(get("/secretariats/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void create_ReturnsPersisted() throws Exception {
        SecretariatDTO input = new SecretariatDTO();
        SecretariatDTO persisted = new SecretariatDTO(); persisted.setId(100);
        when(service.create(any(SecretariatDTO.class))).thenReturn(persisted);

        mockMvc.perform(post("/secretariats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(persisted)));
    }

    @Test
    void update_ReturnsUpdated() throws Exception {
        SecretariatDTO input = new SecretariatDTO(); input.setId(5);
        SecretariatDTO updated = new SecretariatDTO(); updated.setId(5);
        when(service.update(any(SecretariatDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/secretariats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    void delete_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/secretariats/7"))
                .andExpect(status().isOk());
        Mockito.verify(service).delete(7);
    }
}
