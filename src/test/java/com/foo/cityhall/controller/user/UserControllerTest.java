package com.foo.cityhall.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.cityhall.model.dto.user.UserDTO;
import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.service.BaseService;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseService<User, UserDTO, Integer> service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void index_ReturnsPage() throws Exception {
        UserDTO u1 = new UserDTO(); u1.setId(1); u1.setUsername("alice");
        UserDTO u2 = new UserDTO(); u2.setId(2); u2.setUsername("bob");
        var page = new PageImpl<>(List.of(u1, u2), PageRequest.of(0, 2), 2);
        when(service.index(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/users").param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].username").value("alice"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void read_ReturnsDto() throws Exception {
        UserDTO dto = new UserDTO(); dto.setId(10); dto.setUsername("john");
        when(service.read(10)).thenReturn(dto);

        mockMvc.perform(get("/users/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void create_ReturnsPersisted() throws Exception {
        UserDTO input = new UserDTO(); input.setUsername("new"); input.setPassword("raw");
        UserDTO persisted = new UserDTO(); persisted.setId(100); persisted.setUsername("new");
        when(service.create(any(UserDTO.class))).thenReturn(persisted);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.username").value("new"));
    }

    @Test
    void update_ReturnsUpdated() throws Exception {
        UserDTO input = new UserDTO(); input.setId(5); input.setUsername("upd");
        UserDTO updated = new UserDTO(); updated.setId(5); updated.setUsername("upd");
        when(service.update(any(UserDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.username").value("upd"));
    }

    @Test
    void delete_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/users/7"))
                .andExpect(status().isOk());
        Mockito.verify(service).delete(7);
    }
}
