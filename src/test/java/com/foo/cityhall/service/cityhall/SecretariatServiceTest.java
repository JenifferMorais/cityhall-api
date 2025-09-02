package com.foo.cityhall.service.cityhall;


import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;
import com.foo.cityhall.model.mapper.BaseMapper;
import com.foo.cityhall.repository.BaseRepository;
import com.foo.cityhall.service.city​hall.SecretariatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecretariatServiceTest {

    @Mock
    private BaseMapper<Secretariat, SecretariatDTO> mapper;

    @Mock
    private BaseRepository<Secretariat, Integer> repository;

    private SecretariatService service;

    @BeforeEach
    void setUp() {
        service = new SecretariatService(mapper, repository);
    }

    @Test
    void create_MapsAndSaves_ReturnsDto() {
        SecretariatDTO in = new SecretariatDTO();
        Secretariat entity = new Secretariat();

        when(mapper.toEntity(in)).thenReturn(entity);

        when(repository.save(any(Secretariat.class))).thenAnswer(inv -> {
            Secretariat persisted = new Secretariat();
            persisted.setId(1);
            return persisted;
        });

        when(mapper.toDto(any(Secretariat.class))).thenAnswer(inv -> {
            Secretariat e = inv.getArgument(0);
            SecretariatDTO d = new SecretariatDTO();
            d.setId(e.getId());
            return d;
        });

        SecretariatDTO out = service.create(in);

        assertNotNull(out);
        assertEquals(1, out.getId());

        var captor = ArgumentCaptor.forClass(Secretariat.class);
        verify(repository).save(captor.capture());
        assertNull(captor.getValue().getId());
    }


    @Test
    void read_ReturnsDto() {
        Secretariat ent = new Secretariat();
        ent.setId(10);

        when(repository.findById(10)).thenReturn(Optional.of(ent));
        when(mapper.toDto(ent)).thenAnswer(inv -> {
            Secretariat e = inv.getArgument(0);
            SecretariatDTO d = new SecretariatDTO();
            d.setId(e.getId());
            return d;
        });

        SecretariatDTO dto = service.read(10);

        assertEquals(10, dto.getId());
        verify(repository).findById(10);
    }

    @Test
    void update_FindsEditsAndSaves_ReturnsDto() {
        SecretariatDTO in = new SecretariatDTO();
        in.setId(5);

        Secretariat existing = new Secretariat();
        existing.setId(5);

        when(repository.findById(5)).thenReturn(Optional.of(existing));
        doAnswer(inv -> null).when(mapper).update(existing, in);
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toDto(existing)).thenAnswer(inv -> {
            Secretariat e = inv.getArgument(0);
            SecretariatDTO d = new SecretariatDTO();
            d.setId(e.getId());
            return d;
        });

        SecretariatDTO out = service.update(in);

        assertEquals(5, out.getId());
        verify(mapper).update(existing, in);
        verify(repository).save(existing);
    }

    @Test
    void index_ReturnsPagedDtos() {
        Secretariat e = new Secretariat();
        e.setId(1);

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(e), Pageable.ofSize(10), 1));
        when(mapper.toDto(any(Secretariat.class))).thenAnswer(inv -> {
            Secretariat s = inv.getArgument(0);
            SecretariatDTO d = new SecretariatDTO();
            d.setId(s.getId());
            return d;
        });

        Page<SecretariatDTO> page = service.index(Pageable.ofSize(10));

        assertEquals(1, page.getTotalPages());
        assertEquals(1, page.getContent().get(0).getId());
    }

    @Test
    void delete_DelegatesToRepository() {
        service.delete(7);
        verify(repository).deleteById(7);
    }
}
