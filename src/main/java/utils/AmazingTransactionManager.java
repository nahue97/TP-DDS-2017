package utils;

import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class AmazingTransactionManager implements WithGlobalEntityManager {

	public AmazingTransactionManager() {
		super();
	}

	public void beginTransaction() {
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
