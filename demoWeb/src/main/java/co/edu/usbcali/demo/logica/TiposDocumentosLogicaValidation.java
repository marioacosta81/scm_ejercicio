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
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.util.GenericaConstraintViolationLogica;

@Service
@Scope("singleton")
public class TiposDocumentosLogicaValidation implements ITiposDocumentosLogica {
	
	private Logger log=LoggerFactory.getLogger(TiposDocumentosLogicaValidation.class);
	
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;
	
	@Autowired
	private Validator validator;
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void grabar(TiposDocumentos tiposDocumentos) throws Exception {
		
		GenericaConstraintViolationLogica<TiposDocumentos> cv= new
				GenericaConstraintViolationLogica<TiposDocumentos>(tiposDocumentos,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		tipoDocumentoDAO.grabar(tiposDocumentos);
		
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void modificar(TiposDocumentos tiposDocumentos) throws Exception {
		
		GenericaConstraintViolationLogica<TiposDocumentos> cv= new
				GenericaConstraintViolationLogica<TiposDocumentos>(tiposDocumentos,validator);
		StringBuilder stringBuilder = cv.getConstrains();
		
		if(stringBuilder!= null){
			throw new Exception(stringBuilder.toString());
		}
		
		tipoDocumentoDAO.modificar(tiposDocumentos);
		
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void borrar(TiposDocumentos tiposDocumentos) throws Exception {
		
		
		
		
		TiposDocumentos    entity = tipoDocumentoDAO.consultarTipoDocumentoId(tiposDocumentos.getTdocCodigo());
		if(entity== null){
			throw new Exception("El tipo de docuemento a borrar no existe");
		}
		
		tipoDocumentoDAO.borrar(entity);
		
	}

	@Transactional(readOnly=true)
	@Override
	public TiposDocumentos consultarTiposDocumentosPorId(long tipoDocumentoId) throws Exception {
		
		return tipoDocumentoDAO.consultarTipoDocumentoId(tipoDocumentoId);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<TiposDocumentos> consultarTodos() throws Exception {
		return tipoDocumentoDAO.consultarTodos();
	}
	

	

}
