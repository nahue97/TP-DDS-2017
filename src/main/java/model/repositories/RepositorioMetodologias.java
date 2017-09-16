package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import ExceptionsPackage.MetodologiaNotFoundException;
import ExceptionsPackage.TransactionException;
import model.Indicador;
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
		EntityTransaction transaction = transactionManager.getTransaction();
		transactionManager.beginTransaction(transaction);
		try {
			metodologias.add(metodologiaNueva);
			transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
			transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
*/	}

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

	public void limpiarRepositorio() {
		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		EntityTransaction transaction = transactionManager.getTransaction();
		transactionManager.beginTransaction(transaction);
		try {
			metodologias = new ArrayList<Metodologia>();
			transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
			transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
	}
}
