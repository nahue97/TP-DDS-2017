package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.SessionFactory;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class AmazingTransactionManager implements WithGlobalEntityManager {
	
	private static EntityManager entityManager;
	private static AmazingTransactionManager instance;
	EntityTransaction tx;
	
	public static synchronized AmazingTransactionManager getInstance() {
		if (instance == null) {
			instance = new AmazingTransactionManager();
		}
		return instance;
	}

	public AmazingTransactionManager() {
		super();
	}
	
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
	public EntityTransaction getTransaction() {
		return tx;
	}

	public void beginTransaction() {
		entityManager = PerThreadEntityManagers.getEntityManager();
		tx = entityManager.getTransaction();
		tx.begin();
	}

	public void commitTransaction() {
		if (tx.isActive()) {
			tx.commit();
		}
	}

	public void rollbackTransaction() {
		if (tx.isActive()) {
			tx.rollback();
		}
	}

}
