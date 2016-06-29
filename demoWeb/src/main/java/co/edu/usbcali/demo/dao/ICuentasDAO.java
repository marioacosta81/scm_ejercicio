package co.edu.usbcali.demo.dao;

import java.util.List;

import co.edu.usbcali.demo.modelo.Cuentas;

public interface ICuentasDAO {
	
	public void grabar(Cuentas cuentas);
	public void modificar(Cuentas cuentas);
	public void borrar(Cuentas cuentas);
	public Cuentas consultarCuentasPorId(String numeroCuenta);
	public List<Cuentas> consultarTodos();
	

}
