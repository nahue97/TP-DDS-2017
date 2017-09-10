package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Empresa {
	
	@Id
	@GeneratedValue
	private int id;
	private String nombre;
	
	public Empresa(){
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
