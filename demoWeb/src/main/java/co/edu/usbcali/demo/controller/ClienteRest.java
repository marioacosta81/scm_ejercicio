package co.edu.usbcali.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.dto.ClienteDTO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RestController
@RequestMapping("/cliente")
public class ClienteRest {

	@Autowired
	private IDelegadoDeNegocio delegadoDeNegocio;


	@RequestMapping(value="/consultarClientePorId/{cliId}",method=RequestMethod.GET)
	public ClienteDTO consultarClientePorId(@PathVariable("cliId")long cliId)throws Exception{

		Clientes clientes=delegadoDeNegocio.consultarClinetePorId(cliId);
		if(clientes==null){
			throw new Exception("El cliente no existe");
		}

		ClienteDTO clienteDTO=new ClienteDTO();
		clienteDTO.setCliDireccion(clientes.getCliDireccion());
		clienteDTO.setCliId(clientes.getCliId());
		clienteDTO.setCliMail(clientes.getCliMail());
		clienteDTO.setCliNombre(clientes.getCliNombre());
		clienteDTO.setCliTelefono(clientes.getCliTelefono());
		clienteDTO.setTdocCodigo(clientes.getTiposDocumentos().getTdocCodigo());


		return clienteDTO;
	}





	@RequestMapping(value="/consultarClienteTodos",method=RequestMethod.GET)
	public List<ClienteDTO> consultarClienteTodos()throws Exception{

		List<ClienteDTO>  listClientesDTO = new ArrayList<ClienteDTO>();


		List<Clientes> losClientes=delegadoDeNegocio.consultarTodosClientes();
		if(losClientes==null  || losClientes.isEmpty() ){
			throw new Exception("La lista de clientes esta vacia ");
		}

		for(Clientes clientes:losClientes){
			ClienteDTO clienteDTO=new ClienteDTO();
			clienteDTO.setCliDireccion(clientes.getCliDireccion());
			clienteDTO.setCliId(clientes.getCliId());
			clienteDTO.setCliMail(clientes.getCliMail());
			clienteDTO.setCliNombre(clientes.getCliNombre());
			clienteDTO.setCliTelefono(clientes.getCliTelefono());
			clienteDTO.setTdocCodigo(clientes.getTiposDocumentos().getTdocCodigo());

			listClientesDTO.add(clienteDTO);
		}

		return listClientesDTO;
	}


	@RequestMapping(value="/grabar",method=RequestMethod.POST)
	public void grabar(@RequestBody  ClienteDTO clienteDTO)throws Exception{

		try{
			Clientes clientes = new Clientes();

			TiposDocumentos tiposDocumentos = delegadoDeNegocio.consultarTipoDocumentoId(clienteDTO.getTdocCodigo());
			clientes.setTiposDocumentos(tiposDocumentos);

			clientes.setCliDireccion(clienteDTO.getCliDireccion());
			clientes.setCliId(clienteDTO.getCliId());
			clientes.setCliMail(clienteDTO.getCliMail());
			clientes.setCliNombre(clienteDTO.getCliNombre());
			clientes.setCliTelefono(clienteDTO.getCliTelefono());

			delegadoDeNegocio.grabarClientes(clientes);
		}catch(Exception e){
			throw e;
		}


	}

	@RequestMapping(value="/modificar",method=RequestMethod.PUT)
	public void modificar(@RequestBody ClienteDTO clienteDTO)throws Exception{

		try{
			Clientes clientes =  delegadoDeNegocio.consultarClinetePorId(clienteDTO.getCliId());		
			if( clientes==null ){
				throw new Exception("El cliente a modificar no existe");
			}


			TiposDocumentos tiposDocumentos = delegadoDeNegocio.consultarTipoDocumentoId(clienteDTO.getTdocCodigo());
			clientes.setTiposDocumentos(tiposDocumentos);

			clientes.setCliDireccion(clienteDTO.getCliDireccion());
			clientes.setCliId(clienteDTO.getCliId());
			clientes.setCliMail(clienteDTO.getCliMail());
			clientes.setCliNombre(clienteDTO.getCliNombre());
			clientes.setCliTelefono(clienteDTO.getCliTelefono());

			delegadoDeNegocio.modificarClientes(clientes);
		}catch(Exception e){
			throw e;
		}
	}



	@RequestMapping(value="/borrar/{id}",method=RequestMethod.DELETE)
	public void borrar(@PathVariable("id") Long idCliente )throws Exception{

		try{
			Clientes clientes =  delegadoDeNegocio.consultarClinetePorId(idCliente);		
			if( clientes==null ){
				throw new Exception("El cliente a modificar no existe");
			}

			delegadoDeNegocio.borrarClientes(clientes);
		}catch(Exception e){
			throw e;
		}
	}




}
