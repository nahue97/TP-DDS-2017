package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public enum Criterio {
	@Enumerated(EnumType.STRING)
    MENOR, MAYOR
}