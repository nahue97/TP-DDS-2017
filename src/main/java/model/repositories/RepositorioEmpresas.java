package model.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import model.Empresa;

public class RepositorioEmpresas extends Repositorio<Empresa>{
	
	private static RepositorioEmpresas instance;
	
	public static synchronized RepositorioEmpresas getInstance() {
		if (instance == null)
			instance = new RepositorioEmpresas();
		return instance;
	}
	
	public RepositorioEmpresas() {
		super();
	}

	@Override
	protected Class<Empresa> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean existeEmpresa(Empresa empresa) {
		List<Empresa> empresas = this.searchByExample(empresa);
		if ( empresas.size() == 0)
		{
			return false;
		}
		else
		{
			empresa.setId(empresas.get(0).getId());
			return true;	
		}
	}
	
	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Empresa empresa) {
		if (empresa.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", empresa.getNombre()));
		}
	}

}
