package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ExceptionsPackage.MetodologiaNotFoundException;
import model.Metodologia;
import model.Usuario;

public class RepositorioMetodologias extends Repositorio<Metodologia> {

	private static RepositorioMetodologias instance;
	
	public RepositorioMetodologias() {
		super();
	}

	public static synchronized RepositorioMetodologias getInstance() {
		if (instance == null)
			instance = new RepositorioMetodologias();
		return instance;
	}
	
	public void agregarMetodologias(List<Metodologia> _metodologias) {
		for (Metodologia metodologia : _metodologias)
			this.add(metodologia);
	}

	public List<String> getNombresDeMetodologias() {
		List<Metodologia> _metodologias = new ArrayList<>();
		_metodologias.addAll(this.getAll());
		List<String> nombres = _metodologias.stream().map(metodologia -> metodologia.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	public Boolean existeNombreMetodologia(String nuevaMetodologia) {
		return this.getNombresDeMetodologias().contains(nuevaMetodologia);
	}

	@Override
	protected Class<Metodologia> getEntityType() {
		return Metodologia.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Metodologia metodologia) {
		if (metodologia.getId() != null) {
			criteria.add(Restrictions.eq("id", metodologia.getId()));
		}
		if (metodologia.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", metodologia.getNombre()));
		}
		if (metodologia.getUsuario() != null){
			criteria.add(Restrictions.eq("usuario", metodologia.getUsuario()));
		}
	}

	public Metodologia getMetodologiaPorNombre(String metodologia) {
		List<Metodologia> result = this.searchByExample(new Metodologia(metodologia, null));
		if (!result.isEmpty()) {
			return result.get(0);
		}
		
		throw new MetodologiaNotFoundException("Metodologia no encontrada: " + metodologia);
	}

	public void limpiarRepositorio() {
		this.getAll().forEach(this::delete);
	}

	@Override
	public void add(Metodologia t) {
		t.getReglas().forEach(r -> r.setMetodologia(t));
		super.add(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Metodologia> getAllFromUserId(Long id) {
		Session session = sessionFactory.openSession();
		try {
			Usuario user = new Usuario(null, null);
			user.setId(id);
			Metodologia metodologia = new Metodologia(null, null,user);
			Criteria criteria = session.createCriteria(this.getEntityType());
			this.addCriteriaToSearchByExample(criteria, metodologia);
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

}
