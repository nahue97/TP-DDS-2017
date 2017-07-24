package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Metodologia;


public class RepositorioDeMetodologias {
	
	private static RepositorioDeMetodologias instance;

	private List<Metodologia> metodologias = new ArrayList<Metodologia>();

	public RepositorioDeMetodologias(){
		super();
	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}
	
	public static synchronized RepositorioDeMetodologias getInstance() {
		if (instance == null)
			instance = new RepositorioDeMetodologias();
		return instance;
	}
	
	public List<String> getNombresDeMetodologias() {
		List<Metodologia> _metodologias = new ArrayList<>();
		_metodologias.addAll(metodologias);
		List<String> nombres = _metodologias.stream().map(metodologia -> metodologia.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}
	
}
