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
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class UsuariosLogicaValidation implements IUsuariosLogica {
	
	private Logger log=LoggerFactory.getLogger(UsuariosLogicaValidation.class);
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Usuarios usuarios) throws Exception {
		
		GenericaConstraintViolationLogica<Usuarios> cv= new
				GenericaConstraintViolationLogica<Usuarios>(usuarios,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTipoUsuarioPorId(usuarios.getTiposUsuarios().getTusuCodigo()); 
		if(tiposUsuarios==null  ){
			throw new Exception("El tipo de usuario no existe");
		}
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.grabar(usuarios);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Usuarios usuarios) throws Exception {
		
		GenericaConstraintViolationLogica<Usuarios> cv= new
				GenericaConstraintViolationLogica<Usuarios>(usuarios,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTipoUsuarioPorId(usuarios.getTiposUsuarios().getTusuCodigo()); 
		if(tiposUsuarios==null  ){
			throw new Exception("El tipo de usuario no existe");
		}
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.modificar(usuarios);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Usuarios usuarios) throws Exception {
		
		
		Usuarios    entity = usuariosDAO.consultarUsuarioPorId(usuarios.getUsuCedula());
		if(entity== null){
			throw new Exception("El usuario a borrar no existe");
		}
		
		usuariosDAO.borrar(entity);
		
		
	}

	@Override
	@Transactional(readOnly=true)
	public Usuarios consultarUsuarioPorId(long usuarioId) throws Exception {
		return usuariosDAO.consultarUsuarioPorId(usuarioId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuarios> consultarTodos() throws Exception {
		return usuariosDAO.consultarTodos();
	}
		

	

}
