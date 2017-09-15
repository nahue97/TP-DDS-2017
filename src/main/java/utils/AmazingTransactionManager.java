package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class AmazingTransactionManager implements WithGlobalEntityManager {
	
	EntityManager entityManager;

	public AmazingTransactionManager() {
		super();
	}
	
	public EntityManager getEntityManager(){
		return entityManager;
	}

	public void beginTransaction() {
		entityManager = PerThreadEntityManagers.getEntityManager();
		entityManager().getTransaction().begin();
	}

	public void commitTransaction() {
		EntityTransaction entityTransaction = entityManager().getTransaction();
		if (entityTransaction.isActive()) {
			entityTransaction.commit();
		}
	}

	public void rollbackTransaction() {
		EntityTransaction entityTransaction = entityManager().getTransaction();
		if (entityTransaction.isActive()) {
			entityTransaction.rollback();
		}
	}

}
