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
	
	public EntityTransaction getTransaction() {
		entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager().getTransaction();
	}

	public void beginTransaction(EntityTransaction transaction) {
		transaction.begin();
	}

	public void commitTransaction(EntityTransaction transaction) {
		if (transaction.isActive()) {
			transaction.commit();
		}
	}

	public void rollbackTransaction(EntityTransaction transaction) {
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}

}
