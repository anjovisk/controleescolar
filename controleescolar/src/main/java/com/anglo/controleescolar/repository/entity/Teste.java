package com.anglo.controleescolar.repository.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Table(name="testes")
@javax.persistence.Entity
@Component
public class Teste implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4615162503077782357L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	@Column(nullable = false, length=100)
	private String name;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultOrdinationField() {
		return "name";
	}
}
