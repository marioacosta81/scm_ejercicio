package co.edu.usbcali.demo.logica;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class ConsignacionesLogicaValidation implements IConsignacionesLogica {
	
	private Logger log=LoggerFactory.getLogger(ConsignacionesLogicaValidation.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Consignaciones consignaciones) throws Exception {
		
		GenericaConstraintViolationLogica<Consignaciones> cv= new
				GenericaConstraintViolationLogica<Consignaciones>(consignaciones,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(consignaciones.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		consignaciones.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(consignaciones.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuentas);
		
		consignacionesDAO.grabar(consignaciones);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Consignaciones consignaciones) throws Exception {
		
		GenericaConstraintViolationLogica<Consignaciones> cv= new
				GenericaConstraintViolationLogica<Consignaciones>(consignaciones,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(consignaciones.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		consignaciones.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(consignaciones.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuentas);
		
		consignacionesDAO.modificar(consignaciones); 
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Consignaciones consignaciones) throws Exception {
		
		
		GenericaConstraintViolationLogica<Consignaciones> cv= new
				GenericaConstraintViolationLogica<Consignaciones>(consignaciones,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		Consignaciones entity = consignacionesDAO.consultarConsignacionesPorId(consignaciones.getId());
		if(entity==null){
			throw new Exception("La consignacion que desea eliminar no existe");
		}
		
		consignacionesDAO.borrar(entity);
		
		
	}

	@Override
	@Transactional(readOnly=true)
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId consignacionesId) throws Exception {
		return consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Consignaciones> consultarTodos() throws Exception {
		return consignacionesDAO.consultarTodos();
	}

	
	
	
	

}
