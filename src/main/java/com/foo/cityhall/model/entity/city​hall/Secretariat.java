package com.foo.cityhall.model.entity.city​hall;

import com.foo.cityhall.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "secretariats", schema = "city_hall")
public class Secretariat extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	@Column(name = "acronym", length = 15, nullable = false)
	private String acronym;

	public Secretariat() {
		super();
	}

	public Secretariat(int id) {
		super();
		super.setId(id);
	}

}
