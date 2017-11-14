package model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.uqbar.commons.utils.Observable;

@Observable
@Entity
@Table(name = "usuarios")

public class Usuario extends PersistentEntity{

	private String username;
	private String password;

	public Usuario(){
		
	}
	
	public Usuario(String nombre, String password) {
		super();
		this.username = nombre;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
