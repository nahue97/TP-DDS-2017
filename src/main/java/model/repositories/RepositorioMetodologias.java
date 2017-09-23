package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import ExceptionsPackage.MetodologiaNotFoundException;
import model.Metodologia;

public class RepositorioMetodologias extends Repositorio<Metodologia> {

	private static RepositorioMetodologias instance;

	private List<Metodologia> metodologias = new ArrayList<Metodologia>();

	public RepositorioMetodologias() {
		super();
	}

	public static synchronized RepositorioMetodologias getInstance() {
		if (instance == null)
			instance = new RepositorioMetodologias();
		return instance;
	}

	public void agregarMetodologia(Metodologia metodologiaNueva) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(metodologiaNueva);
		tx.commit();
	}

	public List<Metodologia> getMetodologias() {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Metodologia> result = entityManager.createQuery("SELECT m FROM Metodologia m", Metodologia.class);
		List<Metodologia> metodologias = result.getResultList();
		return metodologias;
	}

	public List<String> getNombresDeMetodologias() {
		List<Metodologia> _metodologias = new ArrayList<>();
		_metodologias.addAll(getMetodologias());
		List<String> nombres = _metodologias.stream().map(metodologia -> metodologia.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	public Metodologia getMetodologiaPorNombre(String nombreMetodologia) {
		for (Metodologia metodologia : getMetodologias()) {
			if (metodologia.getNombre().equals(nombreMetodologia)) {
				return metodologia;
			}
		}
		throw new MetodologiaNotFoundException("No se encuentra una metodologia llamada " + nombreMetodologia);
	}

	public Boolean existeNombreMetodologia(String nuevaMetodologia) {
		return this.getNombresDeMetodologias().contains(nuevaMetodologia);
	}

	@Override
	protected Class<Metodologia> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Metodologia t) {
		// TODO Auto-generated method stub
		
	}

}
