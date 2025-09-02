package com.foo.cityhall.model.dto;

public abstract class BaseEntityDTO<T> {
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
}
