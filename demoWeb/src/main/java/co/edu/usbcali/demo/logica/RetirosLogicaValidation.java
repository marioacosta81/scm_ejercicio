package co.edu.usbcali.demo.logica;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class RetirosLogicaValidation implements IRetirosLogica {
	
	private Logger log=LoggerFactory.getLogger(RetirosLogicaValidation.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private Validator validator;
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Retiros retiros) throws Exception {
		
		
		GenericaConstraintViolationLogica<Retiros> cv= new
				GenericaConstraintViolationLogica<Retiros>(retiros,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(retiros.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		retiros.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(retiros.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuentas);
		
		retirosDAO.grabar(retiros);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Retiros retiros) throws Exception {
		
		GenericaConstraintViolationLogica<Retiros> cv= new
				GenericaConstraintViolationLogica<Retiros>(retiros,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(retiros.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		retiros.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(retiros.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuentas);
		
		retirosDAO.modificar(retiros);  
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Retiros retiros) throws Exception {
		
		
		
		Retiros entity = retirosDAO.consultarRetiroPorId(retiros.getId());
		if(entity==null){
			throw new Exception("El retiro que desea eliminar no existe");
		}
		
		
		retirosDAO.borrar(entity); 
		
	}

	@Override
	@Transactional(readOnly=true)
	public Retiros consultarRetirosPorId(RetirosId retirosId) throws Exception {
		
		
		return retirosDAO.consultarRetiroPorId(retirosId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Retiros> consultarTodos() throws Exception {
		return retirosDAO.consultarTodos();
	}

	

	
	

}
