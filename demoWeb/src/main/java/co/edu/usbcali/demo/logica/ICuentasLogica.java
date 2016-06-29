package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.Cuentas;

public interface ICuentasLogica {
	
	public void grabar(Cuentas cuentas)throws Exception;
	public void modificar(Cuentas cuentas)throws Exception;
	public void borrar(Cuentas cuentas)throws Exception;
	public Cuentas consultarCuentasPorId(String  numeroCuenta)throws Exception;
	public List<Cuentas> consultarTodos()throws Exception;

}
