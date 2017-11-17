package model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.uqbar.commons.utils.Observable;

import utils.CalculadorDeIndicadores;

@Entity
@Table(name = "indicadoresCalculados")
@AttributeOverrides({  
    @AttributeOverride(name="id", column=@Column(name="id")),  
    @AttributeOverride(name="nombre", column=@Column(name="nombre")),  
    @AttributeOverride(name="formula", column=@Column(name="formula"))
})  
public class IndicadorCalculado extends Indicador {

	@Column(nullable=false)
	private BigDecimal valor;
	@Column(nullable=false)
	private String cuentas;
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Empresa empresa;
	@Column(nullable=false)
	private String periodo;

	public IndicadorCalculado(){
	}
	
	public IndicadorCalculado(Indicador indicador, Empresa empresa, String periodo) {
		super(indicador.getNombre(), indicador.getFormula(), indicador.getUsuario());
		this.empresa = empresa;
		this.periodo = periodo;
		this.usuario = indicador.getUsuario();
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicador);
		valor = CalculadorDeIndicadores.getInstance().calcularIndicador(indicador, empresa, periodo);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getCuentas() {
		return cuentas;
	}

	public void setCuentas(String cuentas) {
		this.cuentas = cuentas;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
