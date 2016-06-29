package co.edu.usbcali.demo.controller.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import co.edu.usbcali.demo.dto.ClienteDTO;

public class ClienteRestTest {
	
	private static final Logger log=LoggerFactory.getLogger(ClienteRestTest.class);

	@Test
	public void atest() {
		RestTemplate restTemplate = new RestTemplate();
		ClienteDTO clienteDTO=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/cliente/consultarClientePorId/101234", ClienteDTO.class);
		assertNotNull("El cliente es null", clienteDTO);
		log.info(" si existe el cliente "+clienteDTO.getCliNombre());
	}
	
	
	
	
	@Test
	public void btest() {
		
		ClienteDTO clientesDTO=new ClienteDTO();
		clientesDTO.setCliId(100);
		clientesDTO.setCliDireccion("Avenida Siempre Viva 123");
		clientesDTO.setCliMail("wwww@gmail.com");
		clientesDTO.setCliNombre("Juan fffffff");
		clientesDTO.setCliTelefono("5555555");
		clientesDTO.setTdocCodigo(10);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ClienteDTO> request = new HttpEntity<ClienteDTO>(clientesDTO);
		restTemplate.postForLocation("http://localhost:9098/demoWeb/controller/cliente/grabar",request);
		
		log.info(" el cliente se ha grabado ");
	}
	
	
	@Test
	public void ctest() {
		RestTemplate restTemplate = new RestTemplate();
		ClienteDTO clienteDTO=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/cliente/consultarClientePorId/100", ClienteDTO.class);
		
		clienteDTO.setCliTelefono("9999999");
		
		restTemplate.put("http://localhost:9098/demoWeb/controller/cliente/modificar",clienteDTO);  
	}
	
	
	@Test
	public void dtest() {
		RestTemplate restTemplate = new RestTemplate();
		ClienteDTO clienteDTO=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/cliente/consultarClientePorId/100", ClienteDTO.class);
		
		restTemplate.delete("http://localhost:9098/demoWeb/controller/cliente/borrar/"+clienteDTO.getCliId());  

	}

	
	
	/*
	@Test
	public void etest() {
		RestTemplate restTemplate = new RestTemplate();
		//List<ClienteDTO> losClienteDTO=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/cliente/consultarClientePorId/101234", ArrayList.class);
		List<?> lista=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/cliente/consultarClientePorId/101234", List.class);
		
		List<ClienteDTO> losClienteDTO = (List<ClienteDTO>)lista;
		
		assertNotNull("La lista de los clientes es null", losClienteDTO);
		log.info(" si existe la lista de los clientes con tamaño"+ losClienteDTO.size()  );
	}
	*/
	
	

}
