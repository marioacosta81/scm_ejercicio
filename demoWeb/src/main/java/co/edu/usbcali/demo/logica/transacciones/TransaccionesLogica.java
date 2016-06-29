package co.edu.usbcali.demo.logica.transacciones;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@Scope("singleton")
@Component("transaccionesLogica")
public class TransaccionesLogica implements ITransaccionesLogica{
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO; 
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	private Long antiguoSaldo;
	private Long nuevoSaldo;
	
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void retirar(String numeroCuenta,Long idUsuario,Long valor, String clave) throws Exception {
		
		//consultar cuenta
		Cuentas cuenta = cuentasDAO.consultarCuentasPorId(numeroCuenta);
		
		//validar cuenta existe
		if(cuenta == null  ){
			throw new Exception("La transaccion es cancelada porque la cuenta no existe");
		}
		
		//validar cuenta activa
		if(!cuenta.getCueActiva().trim().equalsIgnoreCase("S")   ){
			throw new Exception("La transaccion es cancelada porque la cuenta no esta activa");
		}
		
		//validar saldo
		if((cuenta.getCueSaldo().longValue() - valor) < 0  ){
			throw new Exception("La transaccion es cancelada porque el saldo es insuficiente");
		}
		
		//validar clave
		if(!cuenta.getCueClave().trim().equalsIgnoreCase(clave)){
			throw new Exception("La transaccion es cancelada porque la clave es incorrecta");
		}
		
		//consultar usuario
		Usuarios usuario = usuariosDAO.consultarUsuarioPorId(idUsuario);
		
		// ingresar retiro
		Retiros retiro = new Retiros();
		
		RetirosId id = new RetirosId(); 
		id.setCueNumero(numeroCuenta);
		//consultar next retCodigo
		Long retCodigo = retirosDAO.getNextRetCodigo();
		id.setRetCodigo(retCodigo);
		retiro.setId(id);
		
		retiro.setCuentas(cuenta);
		retiro.setRetDescripcion("Retiro Transaccion");
		retiro.setRetFecha(new java.util.Date());
		retiro.setRetValor( BigDecimal.valueOf(valor));
		retiro.setUsuarios(usuario);
		retirosDAO.grabar(retiro);
		
		
		
		//actualizar saldo cuenta 
		cuenta.setCueSaldo(BigDecimal.valueOf( cuenta.getCueSaldo().longValue() - valor ));
		cuentasDAO.modificar(cuenta);

		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void consignar(String numeroCuenta,Long idUsuario,Long valor, String clave) throws Exception {
		
		//consultar cuenta
		Cuentas cuenta = cuentasDAO.consultarCuentasPorId(numeroCuenta);
		
		//validar cuenta existe
		if(cuenta == null  ){
			throw new Exception("La transaccion es cancelada porque la cuenta no existe");
		}
		
		//validar cuenta activa
		if(!cuenta.getCueActiva().trim().equalsIgnoreCase("S")   ){
			throw new Exception("La transaccion es cancelada porque la cuenta no esta activa");
		}
		
		
		//validar clave
		if(!cuenta.getCueClave().trim().equalsIgnoreCase(clave)){
			throw new Exception("La transaccion es cancelada porque la clave es incorrecta");
		}
		
		//consultar usuario
		Usuarios usuario = usuariosDAO.consultarUsuarioPorId(idUsuario);
		
		// ingresar consignacion
		
		Consignaciones consignacion = new Consignaciones();
		
		ConsignacionesId id = new ConsignacionesId();
		id.setCueNumero(numeroCuenta);
		//consultar next conCodigo
		Long conCodigo = consignacionesDAO.getNextConCodigo();
		id.setConCodigo(conCodigo);
		consignacion.setId(id);
		
		consignacion.setCuentas(cuenta);
		consignacion.setConDescripcion("Consignacion");
		consignacion.setConFecha(new java.util.Date());
		consignacion.setConValor( BigDecimal.valueOf(valor));
		consignacion.setUsuarios(usuario);
		
		consignacionesDAO.grabar(consignacion);
		
		
		//actualizar saldo cuenta 
		cuenta.setCueSaldo(BigDecimal.valueOf( cuenta.getCueSaldo().longValue() + valor ));
		cuentasDAO.modificar(cuenta);
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public Long getAntiguoSaldo() {
		return antiguoSaldo;
	}

	public void setAntiguoSaldo(Long antiguoSaldo) {
		this.antiguoSaldo = antiguoSaldo;
	}

	public Long getNuevoSaldo() {
		return nuevoSaldo;
	}

	public void setNuevoSaldo(Long nuevoSaldo) {
		this.nuevoSaldo = nuevoSaldo;
	}
	
	
	
	
	
	
}
