package com.foo.portifolio.allspring.model.dto.city​hall;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoDTO extends BaseEntityDTO<Integer> {
	private String title;
	private String content;
	private LocalDateTime creationData;
	private int stateId;

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

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
}
