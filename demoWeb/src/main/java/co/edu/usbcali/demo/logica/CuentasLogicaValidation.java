package co.edu.usbcali.demo.logica;

import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class CuentasLogicaValidation implements ICuentasLogica {
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private Validator validator;
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Cuentas cuentas) throws Exception {
		
		GenericaConstraintViolationLogica<Cuentas> cv= new
				GenericaConstraintViolationLogica<Cuentas>(cuentas,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		Clientes clientes = clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if(clientes==null){
			throw new Exception("El cliente no existe");
		}
		cuentas.setClientes(clientes);
		
		cuentasDAO.grabar(cuentas);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Cuentas cuentas) throws Exception {
		
		GenericaConstraintViolationLogica<Cuentas> cv= new
				GenericaConstraintViolationLogica<Cuentas>(cuentas,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		Clientes clientes = clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if(clientes==null){
			throw new Exception("El cliente no existe");
		}
		cuentas.setClientes(clientes);
		cuentasDAO.grabar(cuentas);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Cuentas cuentas) throws Exception {
		
		
		
		Cuentas entity = cuentasDAO.consultarCuentasPorId(cuentas.getCueNumero());
		if(entity==null){
			throw new Exception("La cuenta que desea eliminar no existe");
		}
		cuentasDAO.borrar(entity);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Cuentas consultarCuentasPorId(String  numeroCuenta) throws Exception {
		return cuentasDAO.consultarCuentasPorId(numeroCuenta);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cuentas> consultarTodos() throws Exception {
		return cuentasDAO.consultarTodos();
	}
	
	

}
