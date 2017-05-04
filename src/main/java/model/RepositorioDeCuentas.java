package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;

public class RepositorioDeCuentas {

	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private Boolean numeracionBase0 = true;
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	//Aï¿½adir cuentas a la lista forzosamente
	
	public void agregarCuenta(Cuenta cuenta){
		cuentas.add(cuenta);
	}
	
	//Mï¿½todos para agregar cuentas que respetan un orden lï¿½gico en los ID
	
	public void agregarCuentaConIdAutogenerado(Cuenta cuenta){
		//Bï¿½sicamente ignorar el ID que viene de la cuenta y meterle el nuestro
		//Creï¿½ una copia de esa cuenta para no cambiarle el id a la original
		Cuenta _cuenta = cuenta;
		
		_cuenta.setId(getIdForNextCuenta());
		agregarCuenta(_cuenta);
	}
	
	public void agregarCuentasConIdAutogenerado(List<Cuenta> _cuentas){
		for(Cuenta cuenta: _cuentas)
			agregarCuentaConIdAutogenerado(cuenta);
	}
	
	//Metodos para remover cuentas de la lista
	
	public void removerCuenta(Cuenta cuenta){
		if(cuentas.contains(cuenta))
			cuentas.remove(cuenta);
		else
			throw new Error("La cuenta no existe");
	}
	
	public void removerCuentaPorId(int id){
		removerCuenta(getCuentaPorId(id));
	}
	
	public void removerCuentas(List<Cuenta> cuentasABorrar){
		for(Cuenta cuenta: cuentasABorrar)
			removerCuenta(cuenta);
	}
	
	public void removerCuentasPorId(List <Integer> ids){
		for(Integer id: ids)
			removerCuentaPorId(id);
	}
	
	//Utilidades
	
	private int getIdForNextCuenta(){
		//Solo funciona si las cuentas estï¿½n ordenadas dentro de la lista
		if(size() != 0){
			Cuenta ultimaCuenta = cuentas.get(size() - 1);
			return ultimaCuenta.getId() + 1;
		}
		else{
			if(numeracionBase0)
				return 0;
			else
				return 1;
		}
	}
	
	public void regenerarLosId(){
		//Regenera los ID de las cuentas segun su posisicon en la lista
		if(size()==0) throw new Error("Repositorio vacio");
		
		int i = (numeracionBase0)? 0:1;
		
		for(Cuenta cuenta: cuentas)
			cuenta.setId(i++);
	}
	
	public void reordenarCuentasPorId(){
		//Reordena las cuentas segun sus id, no cambia nada en las cuentas
		if(size()==0) throw new Error("Repositorio vacio");
		cuentas = cuentas.stream()
				.sorted(Comparator.comparingInt(Cuenta::getId))
				.collect(Collectors.toList());
	}
	
	public int size(){
		return cuentas.size();
	}
	
	public void setNumeracionBase0(){
		numeracionBase0=true;
	}
	public void setNumeracionBase1(){
		numeracionBase0=false;
	}
	
	public  Cuenta getCuentaPorId(int id){
		for(Cuenta cuenta: cuentas)
			if(cuenta.getId() == id)
				return cuenta;
			
		throw new Error("No se encuentra una cuenta con ID: " + id);
	}
	
	public List<Cuenta> getCuentasPorId(List<Integer> ids){
		List<Cuenta> _cuentas = new ArrayList<Cuenta>();
		
		for(int id: ids)
			_cuentas.add(getCuentaPorId(id));
		return _cuentas;
	}
	
	//Filtrar cuentas del repositorio
	
	public  List<Cuenta> filtarCuentasPorTipo(String tipo){
		List<Cuenta> _cuentas = cuentas.stream()
							.filter(cuenta -> cuenta.getTipo() == tipo)
							.collect(Collectors.toList());
		return _cuentas;
	}
	
	public List<Cuenta> filtrarCuentasPorPeriodo(String periodo){
		List<Cuenta> _cuentas = cuentas.stream()
				.filter(cuenta -> cuenta.getPeriodo() == periodo)
				.collect(Collectors.toList());
		return _cuentas;
	}
	
	public List<Cuenta> filtrarCuentasPorEmpresa(String empresa){
		List<Cuenta> _cuentas = cuentas.stream()
				.filter(cuenta -> cuenta.getEmpresa() == empresa)
				.collect(Collectors.toList());
		return _cuentas;
	}
	
	public List<Cuenta> filtrarCuentasPorValor(long valor){
		List<Cuenta> _cuentas = cuentas.stream()
				.filter(cuenta -> cuenta.getValor() == valor)
				.collect(Collectors.toList());
		return _cuentas;
	}
	
	//Devuelven una lista ordenada de determinada manera, sin alterar las propias
	
	/* Estos métodos de ordenamiento funcionan mal
	public List<Cuenta> getCuentasOrdenadasPorValor(){
		List<Cuenta> _cuentas = cuentas.stream()
								.sorted(Comparator.comparingLong(Cuenta::getValor))
								.collect(Collectors.toList());
		return _cuentas;
	}
	
	
	public List<Cuenta> getCuentasOrdenadasPorEmpresa(){
		List<Cuenta> _cuentas = cuentas.stream()
								.sorted(Comparator.comparing(Cuenta::getEmpresa))
								.collect(Collectors.toList());
		return _cuentas;
	}
	
	public List<Cuenta> getCuentasOrdenadasPorPeriodo(){
		List<Cuenta> _cuentas = cuentas.stream()
								.sorted(Comparator.comparing(Cuenta::getPeriodo))
								.collect(Collectors.toList());
		return _cuentas;
	}*/
}
