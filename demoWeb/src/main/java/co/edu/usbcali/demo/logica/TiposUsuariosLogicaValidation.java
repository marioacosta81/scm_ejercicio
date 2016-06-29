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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class TiposUsuariosLogicaValidation implements ITiposUsuariosLogica {
	
	private Logger log=LoggerFactory.getLogger(TiposUsuariosLogicaValidation.class);
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	@Autowired
	private Validator validator;
	
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(TiposUsuarios tiposUsuarios) throws Exception {
		
		GenericaConstraintViolationLogica<TiposUsuarios> cv= new
				GenericaConstraintViolationLogica<TiposUsuarios>(tiposUsuarios,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		tiposUsuariosDAO.grabar(tiposUsuarios);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(TiposUsuarios tiposUsuarios) throws Exception {

		GenericaConstraintViolationLogica<TiposUsuarios> cv= new
				GenericaConstraintViolationLogica<TiposUsuarios>(tiposUsuarios,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		tiposUsuariosDAO.modificar(tiposUsuarios);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(TiposUsuarios tiposUsuarios) throws Exception {
		
		
		
		TiposUsuarios entity = tiposUsuariosDAO.consultarTipoUsuarioPorId(tiposUsuarios.getTusuCodigo());
		if(entity== null){
			throw new Exception("El tipo de usuario a borrar no existe");
		}
		
		tiposUsuariosDAO.borrar(entity);
		
	}

	@Override
	@Transactional(readOnly=true)
	public TiposUsuarios consultarTiposUsuariosPorId(long tiposUsuariosId) throws Exception {
		return tiposUsuariosDAO.consultarTipoUsuarioPorId(tiposUsuariosId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TiposUsuarios> consultarTodos() throws Exception {
		return tiposUsuariosDAO.consultarTodos();
	}
	

	

}
