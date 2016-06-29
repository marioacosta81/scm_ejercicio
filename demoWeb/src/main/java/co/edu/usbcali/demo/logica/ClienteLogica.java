//package co.edu.usbcali.demo.logica;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import co.edu.usbcali.demo.dao.IClienteDAO;
//import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
//import co.edu.usbcali.demo.modelo.Clientes;
//import co.edu.usbcali.demo.modelo.TiposDocumentos;
//
//@Service
//@Scope("singleton")
//public class ClienteLogica  {
//	
//	@Autowired
//	private IClienteDAO clienteDAO;
//	
//	@Autowired
//	private ITipoDocumentoDAO tipoDocumentoDAO;
//	
//
//	@Override
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
//	public void grabar(Clientes clientes) throws Exception {
//		if(clientes==null){
//			throw new Exception("El clientes es null");
//		}
//		if(clientes.getCliDireccion()==null || clientes.getCliDireccion().trim().equals("")==true){
//			throw new Exception("La direccion es obligatoria");
//		}
//		if(clientes.getCliId()==0){
//			throw new Exception("El id es obligatoria");
//		}
//		if(clientes.getCliMail()==null || clientes.getCliMail().trim().equals("")==true){
//			throw new Exception("El mail es obligatoria");
//		}
//		if(clientes.getCliNombre()==null || clientes.getCliNombre().trim().equals("")==true){
//			throw new Exception("El nombre es obligatoria");
//		}		
//		if(clientes.getCliTelefono()==null || clientes.getCliTelefono().trim().equals("")==true){
//			throw new Exception("El telefono es obligatoria");
//		}		
//		if(clientes.getTiposDocumentos()==null){
//			throw new Exception("El tipo de documento es obligatoria");
//		}
//		
//		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTipoDocumentoId(clientes.getTiposDocumentos().getTdocCodigo());
//		if(tiposDocumentos==null){
//			throw new Exception("El tipo de documento no existe");
//		}
//		clientes.setTiposDocumentos(tiposDocumentos);
//		
//		clienteDAO.grabar(clientes);
//		
//	}
//
//	@Override
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
//	public void modificar(Clientes clientes) throws Exception {
//		if(clientes==null){
//			throw new Exception("El clientes es null");
//		}
//		if(clientes.getCliDireccion()==null || clientes.getCliDireccion().trim().equals("")==true){
//			throw new Exception("La direccion es obligatoria");
//		}
//		if(clientes.getCliId()==0){
//			throw new Exception("El id es obligatoria");
//		}
//		if(clientes.getCliMail()==null || clientes.getCliMail().trim().equals("")==true){
//			throw new Exception("El mail es obligatoria");
//		}
//		if(clientes.getCliNombre()==null || clientes.getCliNombre().trim().equals("")==true){
//			throw new Exception("El nombre es obligatoria");
//		}		
//		if(clientes.getCliTelefono()==null || clientes.getCliTelefono().trim().equals("")==true){
//			throw new Exception("El telefono es obligatoria");
//		}		
//		if(clientes.getTiposDocumentos()==null){
//			throw new Exception("El tipo de documento es obligatoria");
//		}
//		
//		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTipoDocumentoId(clientes.getTiposDocumentos().getTdocCodigo());
//		if(tiposDocumentos==null){
//			throw new Exception("El tipo de documento no existe");
//		}
//		clientes.setTiposDocumentos(tiposDocumentos);
//		
//		clienteDAO.modificar(clientes);
//
//	}
//
//	@Override
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
//	public void borrar(Clientes clientes) throws Exception {
//		if(clientes==null){
//			throw new Exception("El clientes es null");
//		}
//		Clientes entity=clienteDAO.consultarClinetePorId(clientes.getCliId());
//		if(entity==null){
//			throw new Exception("El cliente que desea eliminar no existe");
//		}
//		
//		clienteDAO.borrar(entity);
//
//	}
//
//	@Override
//	@Transactional(readOnly=true)
//	public Clientes consultarClinetePorId(long cliId) throws Exception {
//		return clienteDAO.consultarClinetePorId(cliId);
//	}
//
//	@Override
//	@Transactional(readOnly=true)
//	public List<Clientes> consultarTodos() throws Exception {		
//		return clienteDAO.consultarTodos();
//	}
//
//}
