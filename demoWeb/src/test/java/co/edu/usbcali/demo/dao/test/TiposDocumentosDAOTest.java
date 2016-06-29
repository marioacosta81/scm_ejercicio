package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposDocumentosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposDocumentosDAOTest.class);
	
	@Autowired
	private ITipoDocumentoDAO tiposDocumentoDAO;
	
	private Long cliId=142020L;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocNombre("DUMMY");
		tiposDocumentos.setTdocCodigo(11L);
		
		tiposDocumentoDAO.grabar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposDocumentos tiposDocumentos = tiposDocumentoDAO.consultarTipoDocumentoId(11L); 
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		TiposDocumentos tiposDocumentos = tiposDocumentoDAO.consultarTipoDocumentoId(11L); 
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
		tiposDocumentos.setTdocNombre("DUMMY2");
		
		tiposDocumentoDAO.modificar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposDocumentos tiposDocumentos = tiposDocumentoDAO.consultarTipoDocumentoId(11L); 
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
		tiposDocumentoDAO.borrar(tiposDocumentos);
		
	}

	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposDocumentos> losTipos= tiposDocumentoDAO.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTipos) {
			log.info(tiposDocumentos.getTdocNombre());
		}
	}
	
	

}
