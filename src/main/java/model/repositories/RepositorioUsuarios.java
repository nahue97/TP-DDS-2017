package model.repositories;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import model.Usuario;

public class RepositorioUsuarios extends Repositorio<Usuario> {

	private List<Usuario> usuarios = new LinkedList<>();
	
	private static RepositorioUsuarios instance;
	
	public static synchronized RepositorioUsuarios getInstance() {
		if (instance == null)
			instance = new RepositorioUsuarios();
		return instance;
	}
	
	public Usuario findAny() {
		return usuarios.stream().findAny().orElse(null);
	}
	
	public Usuario findByUsername(int username) {
		throw new RuntimeException("findByUsername aun no esta implementado");
	}

	public void registrar(Usuario usuario) {
		this.add(usuario);
	}

	@Override
	protected Class<Usuario> getEntityType() {
		return Usuario.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Usuario usuario) {
		if (usuario.getId() != null) {
			criteria.add(Restrictions.eq("id", usuario.getId()));
		}
		if (usuario.getUsername() != null){
			if (!usuario.getUsername().isEmpty()){
				criteria.add(Restrictions.eq("username", usuario.getUsername()));
			}
		}
		if (usuario.getPassword() != null){
			if (!usuario.getPassword().isEmpty()){
				criteria.add(Restrictions.eq("password", usuario.getPassword()));
			}
		}	
	}
	
}
