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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuarioLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuarioLogicaTest.class);
	
	@Autowired
	private IUsuariosLogica usuarioLogica;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(3456765L);
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTipoUsuarioPorId(20L);
		usuarios.setTiposUsuarios(tiposUsuarios);
		usuarios.setUsuNombre("Mario Alfonso Acosta");
		usuarios.setUsuLogin("junit_user_login");
		usuarios.setUsuClave("123456");
		
		usuarioLogica.grabar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		Usuarios usuarios = usuarioLogica.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		Usuarios usuarios = usuarioLogica.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
		
		usuarios.setUsuNombre("Mario Acosta");
		usuarioLogica.modificar(usuarios);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		Usuarios usuarios = usuarioLogica.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
		
		usuarioLogica.borrar(usuarios);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<Usuarios> losUsuarios= usuarioLogica.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}
	
	

}
