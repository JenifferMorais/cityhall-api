package com.foo.cityhall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.foo.cityhall.model.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity<ID>, ID> extends JpaRepository<Entity, ID> {

}
