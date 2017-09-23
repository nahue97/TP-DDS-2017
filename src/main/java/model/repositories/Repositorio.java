package model.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.Metodologia;
import model.Regla;

@SuppressWarnings("unchecked")
public abstract class Repositorio<T> {

	protected static final SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure()
		.addAnnotatedClass(Cuenta.class)
		.addAnnotatedClass(Empresa.class)
		.addAnnotatedClass(Indicador.class)
		.addAnnotatedClass(Metodologia.class)
		.addAnnotatedClass(Regla.class)
		.buildSessionFactory();

	protected Session openSession() {
		return sessionFactory.openSession();
	}

	public List<T> allInstances() {
		Session session = sessionFactory.openSession();
		try {
			return session.createCriteria(this.getEntityType()).list();
		} finally {
			session.close();
		}
	}

	protected abstract Class<T> getEntityType();

	protected abstract void addCriteriaToSearchByExample(Criteria criteria, T t);

	public List<T> searchByExample(T t) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			this.addCriteriaToSearchByExample(criteria, t);
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public List<T> getAll() {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void saveOrUpdate(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void delete(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public void update(T t) {
		this.saveOrUpdate(t);
	}

	public void add(T t) {
		this.saveOrUpdate(t);
	}
}