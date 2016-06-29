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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposUsuariosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposUsuariosDAOTest.class);
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposUsuarios tiposUsuarios = new TiposUsuarios(); 
		
		tiposUsuarios.setTusuCodigo(100L);
		tiposUsuarios.setTusuNombre("j_unit_user");
		tiposUsuariosDAO.grabar(tiposUsuarios);
		
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposUsuarios tiposUsuarios  = tiposUsuariosDAO.consultarTipoUsuarioPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);
		log.info( tiposUsuarios.getTusuNombre() );
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		TiposUsuarios tiposUsuarios  = tiposUsuariosDAO.consultarTipoUsuarioPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);
		tiposUsuarios.setTusuNombre("j_unit_user_2");
		
		tiposUsuariosDAO.modificar(tiposUsuarios);
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposUsuarios tiposUsuarios  = tiposUsuariosDAO.consultarTipoUsuarioPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);
		
		tiposUsuariosDAO.borrar(tiposUsuarios);
	}

	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposUsuarios> losTiposUsuarios = tiposUsuariosDAO.consultarTodos();
		for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
			log.info(  tiposUsuarios.getTusuNombre() );
		}
	}
	
	

}
