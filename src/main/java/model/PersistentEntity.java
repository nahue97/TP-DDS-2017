package model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistentEntity {

	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	};
	
	public void setId(Long _id){
		this.id = _id;
	}
}