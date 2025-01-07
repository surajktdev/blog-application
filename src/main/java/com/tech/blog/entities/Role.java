package com.tech.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
}
