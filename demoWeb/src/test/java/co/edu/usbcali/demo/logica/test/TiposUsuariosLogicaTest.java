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

import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposUsuariosLogicaTest {

	private static final Logger log=LoggerFactory.getLogger(TiposUsuariosLogicaTest.class);

	@Autowired
	private ITiposUsuariosLogica tiposUsuariosLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		TiposUsuarios tiposUsuarios = new TiposUsuarios(); 

		tiposUsuarios.setTusuCodigo(100L);
		tiposUsuarios.setTusuNombre("j_unit_user");
		tiposUsuariosLogica.grabar(tiposUsuarios);
	}

	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		TiposUsuarios tiposUsuarios  = tiposUsuariosLogica.consultarTiposUsuariosPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);
		log.info( tiposUsuarios.getTusuNombre() );
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		TiposUsuarios tiposUsuarios  = tiposUsuariosLogica.consultarTiposUsuariosPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);
		tiposUsuarios.setTusuNombre("j_unit_user_2");

		tiposUsuariosLogica.modificar(tiposUsuarios);
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		TiposUsuarios tiposUsuarios  = tiposUsuariosLogica.consultarTiposUsuariosPorId(100L);
		assertNotNull("El tipo de usuario no exsiste",tiposUsuarios);

		tiposUsuariosLogica.borrar(tiposUsuarios);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<TiposUsuarios> losTiposUsuarios = tiposUsuariosLogica.consultarTodos();
		for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
			log.info(  tiposUsuarios.getTusuNombre() );
		}
	}



}
