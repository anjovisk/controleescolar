package com.anglo.controleescolar.logging;

public enum SystemLogAction {
	LOGIN_SUCESSFULLY(0, "Login realizado com sucesso");

	private int id;
	private String description;
	private SystemLogAction value;

	private SystemLogAction(int id, String description) {
		this.id = id;
		this.description = description;
		this.value = this;
	}
	
	public SystemLogAction getValue(){
		value = this;
		return value;
	}

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public int getOrdinal() {
		return this.id;
	}
}
