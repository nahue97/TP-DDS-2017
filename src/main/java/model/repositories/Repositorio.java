package model.repositories;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.Usuario;

@SuppressWarnings("unchecked")
public abstract class Repositorio<T> {

	protected static final Configuration configuration = new Configuration().configure().addAnnotatedClass(Cuenta.class)
			.addAnnotatedClass(Empresa.class).addAnnotatedClass(Indicador.class).addAnnotatedClass(Metodologia.class)
			.addAnnotatedClass(Regla.class).addAnnotatedClass(ReglaTaxativa.class).addAnnotatedClass(ReglaComparativa.class)
			.addAnnotatedClass(Usuario.class).addAnnotatedClass(IndicadorCalculado.class);

	protected static SessionFactory sessionFactory = configuration.buildSessionFactory(
			new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

	protected Session openSession() {
		return sessionFactory.openSession();
	}

	protected abstract Class<T> getEntityType();

	protected abstract void addCriteriaToSearchByExample(Criteria criteria, T t);
	
	protected abstract List<T> getAllFromUserId(Long id);

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
	
	public long count() {
		Criteria criteria = openSession().createCriteria(this.getEntityType());
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.uniqueResult();
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
	
	public List<T> getAllWithSession(Session session) {
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
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
	
	public void saveOrUpdateWithSession(T t, Session session) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
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
	
	public void deleteWithSession(T t, Session session) {
		try {
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		}
	}

	public void update(T t) {
		this.saveOrUpdate(t);
	}

	public void add(T t) {
		this.saveOrUpdate(t);
	}
	
	public void limpiarRepositorio() {
		Session session = sessionFactory.openSession();
		this.getAllWithSession(session).forEach(t -> this.deleteWithSession(t, session));
		session.close();
	}
}