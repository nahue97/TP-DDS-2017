package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import ExceptionsPackage.MetodologiaNotFoundException;
import ExceptionsPackage.TransactionException;
import model.Metodologia;
import utils.AmazingTransactionManager;

public class RepositorioMetodologias implements WithGlobalEntityManager {

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
		
/*		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		transactionManager.beginTransaction();
		try {
			metodologias.add(metodologiaNueva);
			transactionManager.commitTransaction();
		} catch (Throwable e) {
			transactionManager.rollbackTransaction();
			throw new TransactionException(e.getMessage());
		}
*/	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public List<String> getNombresDeMetodologias() {
		List<Metodologia> _metodologias = new ArrayList<>();
		_metodologias.addAll(metodologias);
		List<String> nombres = _metodologias.stream().map(metodologia -> metodologia.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	public Metodologia getMetodologiaPorNombre(String nombreMetodologia) {
		for (Metodologia metodologia : metodologias) {
			if (metodologia.getNombre().equals(nombreMetodologia)) {
				return metodologia;
			}
		}
		throw new MetodologiaNotFoundException("No se encuentra una metodologia llamada " + nombreMetodologia);
	}

	public Boolean existeNombreMetodologia(String nuevaMetodologia) {
		return this.getNombresDeMetodologias().contains(nuevaMetodologia);
	}

	public void limpiarRepositorio() {
		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		transactionManager.beginTransaction();
		try {
			metodologias = new ArrayList<Metodologia>();
			transactionManager.commitTransaction();
		} catch (Throwable e) {
			transactionManager.rollbackTransaction();
			throw new TransactionException(e.getMessage());
		}
	}
}
