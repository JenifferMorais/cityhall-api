package com.foo.cityhall.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.foo.cityhall.model.dto.BaseEntityDTO;
import com.foo.cityhall.model.entity.BaseEntity;
import com.foo.cityhall.service.BaseService;


@ExtendWith(MockitoExtension.class)
class BaseControllerTest {

    static class FooEntity extends BaseEntity<Integer> {}
    static class FooDTO extends BaseEntityDTO<Integer> {}

    @Mock
    private BaseService<FooEntity, FooDTO, Integer> service;
    private BaseController<FooEntity, FooDTO, Integer> controller;

    @BeforeEach
    void setUp() {
        controller = new BaseController<FooEntity, FooDTO, Integer>(service) {};
    }

    @Test
    void index_deveDelegarParaServiceERetornarPagina() {
        Pageable pageable = Pageable.ofSize(10);
        FooDTO dto = new FooDTO();
        Page<FooDTO> expected = new PageImpl<>(List.of(dto), pageable, 1);

        when(service.index(any(Pageable.class))).thenReturn(expected);

        Page<FooDTO> result = controller.index(pageable);

        assertSame(expected, result);
        verify(service).index(pageable);
    }

    @Test
    void read_deveDelegarParaServiceERetornarDTO() {
        Integer id = 42;
        FooDTO expected = new FooDTO();
        when(service.read(id)).thenReturn(expected);

        FooDTO result = controller.read(id);

        assertSame(expected, result);
        verify(service).read(id);
    }

    @Test
    void create_deveDelegarParaServiceERetornarDTO() {
        FooDTO input = new FooDTO();
        FooDTO persisted = new FooDTO();

        when(service.create(input)).thenReturn(persisted);

        FooDTO result = controller.create(input);

        assertSame(persisted, result);
        verify(service).create(input);
    }

    @Test
    void update_deveDelegarParaServiceERetornarDTO() {
        FooDTO input = new FooDTO();
        FooDTO updated = new FooDTO();

        when(service.update(input)).thenReturn(updated);

        FooDTO result = controller.update(input);

        assertSame(updated, result);
        verify(service).update(input);
    }

    @Test
    void delete_deveDelegarParaService() {
        Integer id = 7;

        controller.delete(id);

        verify(service).delete(id);
        verifyNoMoreInteractions(service);
    }
}
