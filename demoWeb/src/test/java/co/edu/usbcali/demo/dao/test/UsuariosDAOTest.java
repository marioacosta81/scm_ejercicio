package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuariosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuariosDAOTest.class);
	
	@Autowired
	private IUsuariosDAO usuarioDAO;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(3456765L);
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTipoUsuarioPorId(20L);
		usuarios.setTiposUsuarios(tiposUsuarios);
		usuarios.setUsuNombre("Mario Alfonso Acosta");
		usuarios.setUsuLogin("junit_user_login");
		usuarios.setUsuClave("123456");
		
		usuarioDAO.grabar(usuarios);
			
	}
	
	
	

	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Usuarios usuarios = usuarioDAO.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Usuarios usuarios = usuarioDAO.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
		
		usuarios.setUsuNombre("Mario Acosta");
		usuarioDAO.modificar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Usuarios usuarios = usuarioDAO.consultarUsuarioPorId(3456765L);
		assertNotNull("El usuario no exsiste",usuarios);
		log.info(usuarios.getUsuLogin());
		
		usuarioDAO.borrar(usuarios);
		
	}

	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Usuarios> losUsuarios= usuarioDAO.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}
	
	

}
