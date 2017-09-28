package model;

import javax.persistence.Entity;

@Entity
public class Empresa extends PersistentEntity{

	private String nombre;
	
	public Empresa(Long id, String nombre){
		this.setId(id);
		this.nombre = nombre;
	}

	public Empresa(String _nombre){
		this.nombre = _nombre;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String _nombre){
		this.nombre = _nombre;
	}
}
