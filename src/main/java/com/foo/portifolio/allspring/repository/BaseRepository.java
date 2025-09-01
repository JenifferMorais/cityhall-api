package com.foo.portifolio.allspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.foo.portifolio.allspring.model.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity<ID>, ID> extends JpaRepository<Entity, ID> {

}
