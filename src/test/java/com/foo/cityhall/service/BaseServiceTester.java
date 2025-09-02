package com.foo.cityhall.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.foo.cityhall.model.dto.BaseEntityDTO;
import com.foo.cityhall.model.entity.BaseEntity;
import com.foo.cityhall.model.mapper.BaseMapper;
import com.foo.cityhall.repository.BaseRepository;

@SpringBootTest
public class BaseServiceTester {
	@Mock
	private BaseMapper<TestEntity, TestDTO> mapper;
	@Mock
	private BaseRepository<TestEntity, Integer> repository;

	private TestBaseService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.service = new TestBaseService(mapper, repository);
	}

	@Test
	void testFindById_Success() {
		TestEntity entity = new TestEntity(1);
		TestDTO dto = new TestDTO(1);

		when(repository.findById(1)).thenReturn(Optional.of(entity));
		when(mapper.toDto(entity)).thenReturn(dto);

		TestDTO result = service.read(1);

		assertNotNull(result);
		assertEquals(1, result.getId());
	}

	@Test
	void testFindByIdNotFound() {
		when(repository.findById(anyInt())).thenReturn(Optional.of(new TestEntity()));

		when(mapper.toDto(any(TestEntity.class))).thenReturn(new TestDTO());

		TestDTO result = service.read(1);

		assertNotNull(result);
	}

	@Test
	void testSaveEntitySuccess() {
		TestDTO dto = new TestDTO();
		TestEntity entity = new TestEntity();
		TestEntity savedEntity = new TestEntity(1);
		TestDTO savedDTO = new TestDTO(1);

		when(mapper.toEntity(dto)).thenReturn(entity);
		when(repository.save(entity)).thenReturn(savedEntity);
		when(mapper.toDto(savedEntity)).thenReturn(savedDTO);

		TestDTO result = service.create(dto);

		assertNotNull(result);
		assertEquals(1, result.getId());
	}

	@Test
	void testDeleteEntitySuccess() {

		service.delete(1);

		verify(repository, times(1)).deleteById(1);
	}

	@Test
	void testDeleteEntityNotFound() {
		doThrow(new IllegalArgumentException()).when(repository).deleteById(1);

		assertThrows(Exception.class, () -> service.delete(1));
	}

	public class TestBaseService extends BaseService<TestEntity, TestDTO, Integer> {

		public TestBaseService(BaseMapper<TestEntity, TestDTO> mapper, BaseRepository<TestEntity, Integer> repository) {
			super(mapper, repository);
		}

	}

	public class TestEntity extends BaseEntity<Integer> {

		public TestEntity(int id) {
			super();
			super.setId(id);
		}

		public TestEntity() {
			super();
		}

	}

	public class TestDTO extends BaseEntityDTO<Integer> {
		public TestDTO() {
			super();
		}

		public TestDTO(int id) {
			super();
			super.setId(id);
		}
	}
}
