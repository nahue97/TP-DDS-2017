package model;

import java.util.List;
import java.util.stream.Collectors;
//Repositorio de Cuentas
public class RepositorioDeCuentas {

	private List<Cuenta> cuentas;
	private Boolean numeracionBase0 = true;
	
	RepositorioDeCuentas(List<Cuenta> _cuentas){
		cuentas = _cuentas;
	}
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(List<Cuenta> _cuentas) {
		this.cuentas = _cuentas;
	}
	
	//Añadir cuentas a la lista forzosamente
	
	public void addCuenta(Cuenta cuenta){
		cuentas.add(cuenta);
	}
	
	public void addCuentas(List<Cuenta> _cuentas){
		cuentas.addAll(_cuentas);
	}
	
	public void crearCuenta(int id, String tipo, String empresa, String periodo, Long valor){
		Cuenta cuenta = new Cuenta(id, tipo, empresa, periodo, valor);
		this.addCuenta(cuenta);
	}
	
	//Métodos para agregar cuentas que respetan un orden lógico en los ID
	
	public void crearCuentaConIdAutogenerado(String tipo, String empresa, String periodo, Long valor){
			this.crearCuenta(this.getIdForNextCuenta(), tipo, empresa, periodo, valor);
	}
	
	public void addCuentaConIdAutogenerado(Cuenta cuenta){
		//Básicamente ignorar el ID que viene de la cuenta y meterle el nuestro
		//Creé una copia de esa cuenta para no cambiarle el id a la original
		Cuenta _cuenta = cuenta;
		
		_cuenta.setId(getIdForNextCuenta());
		this.addCuenta(_cuenta);
	}
	
	public void añadirCuentasConIdAutogenerado(List<Cuenta> cuentas){
		for(Cuenta cuenta: cuentas)
			this.addCuentaConIdAutogenerado(cuenta);
	}
	
	//Métodos para remover cuentas de la lista
	
	public void removerCuenta(Cuenta cuenta){
		cuentas.remove(cuenta);
	}
	
	public void removerCuentaPorId(int id){
		this.removerCuenta(this.getCuentaPorId(id));
	}
	
	public void removerCuentas(List<Cuenta> cuentasABorrar){
		for(Cuenta cuenta: cuentasABorrar)
			this.removerCuenta(cuenta);
	}
	
	//Utilidades
	
	public int getIdForNextCuenta(){
		//Solo funciona si las cuentas están ordenadas dentro de la lista
		if(this.size() != 0){
			Cuenta ultimaCuenta = cuentas.get(this.size() - 1);
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
		//Regenera los ID de las cuentas según su posisicon en la lista
		if(this.size()==0) throw new Error("Repositorio vacío");
		
		int i = (numeracionBase0)? 0:1;
		
		for(Cuenta cuenta: cuentas)
			cuenta.setId(i++);
	}
	
	public void reordenarCuentasPorId(){
		//Reordena las cuentas según sus id, no cambia nada en las cuentas
		if(this.size()==0) throw new Error("Repositorio vacío");
		
		//Cómo usar método sort en cuentas ara que las ordene según ID
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
	
	public Cuenta getCuentaPorId(int id){
		for(Cuenta cuenta: cuentas)
			if(cuenta.getId() == id)
				return cuenta;
			
		throw new Error("No se encuentra una cuenta con ID: " + id);
	}
	
	//Filtrar cuentas del repositorio
	
	public List<Cuenta> filtarCuentasPorTipo(String tipo){
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
}
