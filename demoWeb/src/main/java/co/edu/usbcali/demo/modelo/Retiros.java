package co.edu.usbcali.demo.modelo;
// Generated 22/04/2016 07:50:35 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Retiros generated by hbm2java
 */
public class Retiros implements java.io.Serializable {
	
	@NotNull
	private RetirosId id;
	@NotNull
	private Usuarios usuarios;
	@NotNull
	private Cuentas cuentas;
	@NotNull
	@Min(0)
	private BigDecimal retValor;
	@NotNull
	private Date retFecha;
	@NotNull
	@Length(min=3,max=50)
	private String retDescripcion;

	public Retiros() {
	}

	public Retiros(RetirosId id, Cuentas cuentas, BigDecimal retValor, Date retFecha, String retDescripcion) {
		this.id = id;
		this.cuentas = cuentas;
		this.retValor = retValor;
		this.retFecha = retFecha;
		this.retDescripcion = retDescripcion;
	}

	public Retiros(RetirosId id, Usuarios usuarios, Cuentas cuentas, BigDecimal retValor, Date retFecha,
			String retDescripcion) {
		this.id = id;
		this.usuarios = usuarios;
		this.cuentas = cuentas;
		this.retValor = retValor;
		this.retFecha = retFecha;
		this.retDescripcion = retDescripcion;
	}

	public RetirosId getId() {
		return this.id;
	}

	public void setId(RetirosId id) {
		this.id = id;
	}

	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Cuentas getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}

	public BigDecimal getRetValor() {
		return this.retValor;
	}

	public void setRetValor(BigDecimal retValor) {
		this.retValor = retValor;
	}

	public Date getRetFecha() {
		return this.retFecha;
	}

	public void setRetFecha(Date retFecha) {
		this.retFecha = retFecha;
	}

	public String getRetDescripcion() {
		return this.retDescripcion;
	}

	public void setRetDescripcion(String retDescripcion) {
		this.retDescripcion = retDescripcion;
	}

}
