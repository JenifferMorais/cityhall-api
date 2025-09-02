package com.foo.portifolio.allspring.model.entity.city​hall;

import java.time.LocalDateTime;

import com.foo.portifolio.allspring.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="todo",schema="todos")
public class Todo extends BaseEntity<Integer> {
	@Column(name="title",length = 80,nullable = false)
	private String title;
	
	@Column(name="content",length = 550)
	private String content;
	
	@Column(name="creation_data",nullable = true)
	private LocalDateTime creationData;
	
	@ManyToOne
	@JoinColumn(name = "fk_state")
	private State state;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationData() {
		return creationData;
	}

	public void setCreationData(LocalDateTime creationData) {
		this.creationData = creationData;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
