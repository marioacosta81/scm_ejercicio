package co.edu.usbcali.demo.logica.transacciones;

public interface ITransaccionesLogica {
	
	
	public void retirar(String numeroCuenta,Long idUsuario,Long valor, String clave)throws Exception;
	
	public void consignar(String numeroCuenta,Long idUsuario,Long valor, String clave)throws Exception;
	
	
	
	
}
