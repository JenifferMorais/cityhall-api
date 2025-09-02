package com.foo.portifolio.allspring.model.entity.city​hall;

import java.time.LocalDate;

import com.foo.portifolio.allspring.model.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "servants", schema = "city_hall")
public class Servant extends BaseEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	@Column(name = "name", length = 60, nullable = false)
	private String name;
	
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	
	@Column(name = "birthday")
	private LocalDate birthday;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "secretariat_id", referencedColumnName = "id", nullable = false)
	
	private Secretariat secretariat;

	public Servant() {
		super();
	}

	public Servant(int id) {
		super();
		super.setId(id);
	}


}
