package co.edu.usbcali.demo.logica.test;

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

import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposDocumentosLogicaTest {

	private static final Logger log=LoggerFactory.getLogger(TiposDocumentosLogicaTest.class);

	@Autowired
	private ITiposDocumentosLogica tiposDocumentosLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocNombre("DUMMY");
		tiposDocumentos.setTdocCodigo(11L);
		
		tiposDocumentosLogica.grabar(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogica.consultarTiposDocumentosPorId(11L);  
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogica.consultarTiposDocumentosPorId(11L); 
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
		tiposDocumentos.setTdocNombre("DUMMY2");
		
		tiposDocumentosLogica.modificar(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogica.consultarTiposDocumentosPorId(11L); 
		assertNotNull("El tipo documento no exsiste",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
		tiposDocumentosLogica.borrar(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<TiposDocumentos> losTipos= tiposDocumentosLogica.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTipos) {
			log.info(tiposDocumentos.getTdocNombre());
		}
	}



}
