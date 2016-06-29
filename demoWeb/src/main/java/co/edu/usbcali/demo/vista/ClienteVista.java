package co.edu.usbcali.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@ViewScoped
@ManagedBean
public class ClienteVista {

	private static final Logger log=LoggerFactory.getLogger(ClienteVista.class);

	@ManagedProperty(value="#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;

	List<Clientes> losClientes;

	List<SelectItem> losTiposDocumentos;


	private InputText txtIdentificacion;
	private InputText txtNombre;
	private InputText txtDireccion;
	private InputText txtTelefono;
	private InputText txtMail;

	private SelectOneMenu somTipoDocumento;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;





	@PostConstruct
	public void ini(){
		try{

			//consultar grilla clientes
			losClientes = delegadoDeNegocio.consultarTodosClientes();
			
			//consultar tipos de documentos
			losTiposDocumentos = new ArrayList<SelectItem>();
			List<TiposDocumentos> listTiposDocumentos = delegadoDeNegocio.consultarTodosTiposDocumentos();
			for(TiposDocumentos tp:listTiposDocumentos){
				SelectItem si = new SelectItem(tp.getTdocCodigo(), tp.getTdocNombre());
				losTiposDocumentos.add(si);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}




	public void txtIdentificacionListener(){
		Clientes entity=null;


		try {
			Long id=Long.parseLong(txtIdentificacion.getValue().toString().trim());
			entity=delegadoDeNegocio.consultarClinetePorId(id);
		} catch (Exception e) {
		}

		if(entity==null){
			txtDireccion.resetValue();
			txtMail.resetValue();
			txtNombre.resetValue();
			txtTelefono.resetValue();
			somTipoDocumento.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);
		}else{

			txtDireccion.setValue(entity.getCliDireccion());
			txtMail.setValue(entity.getCliMail());
			txtNombre.setValue(entity.getCliNombre());
			txtTelefono.setValue(entity.getCliTelefono());

			somTipoDocumento.setValue(entity.getTiposDocumentos().getTdocCodigo());

			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);

		}

	}



	public String crearAction(){
		try{
			Clientes clientes= new Clientes();
			
			
			clientes.setCliId( Long.parseLong(   txtIdentificacion.getValue().toString().trim()  ));
			
			
			TiposDocumentos tiposDocumentos = delegadoDeNegocio.consultarTipoDocumentoId(
					Long.parseLong(   somTipoDocumento.getValue().toString().trim())   );
			clientes.setTiposDocumentos(tiposDocumentos);
			
			
			
			clientes.setCliNombre(txtNombre.getValue().toString().trim());
			clientes.setCliDireccion(txtDireccion.getValue().toString().trim());
			clientes.setCliTelefono(txtTelefono.getValue().toString().trim());
			clientes.setCliMail(txtMail.getValue().toString().trim());
			
			delegadoDeNegocio.grabarClientes(clientes);
			
			losClientes = delegadoDeNegocio.consultarTodosClientes();
			
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El cliente fue grabado con exito",""));
			

		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}

	public String modificarAction(){
		try{
			Clientes clientes= new Clientes();
			
			
			clientes.setCliId( Long.parseLong(   txtIdentificacion.getValue().toString().trim()  ));
			
			
			TiposDocumentos tiposDocumentos = delegadoDeNegocio.consultarTipoDocumentoId(
					Long.parseLong(   somTipoDocumento.getValue().toString().trim())   );
			clientes.setTiposDocumentos(tiposDocumentos);
			
			
			
			clientes.setCliNombre(txtNombre.getValue().toString().trim());
			clientes.setCliDireccion(txtDireccion.getValue().toString().trim());
			clientes.setCliTelefono(txtTelefono.getValue().toString().trim());
			clientes.setCliMail(txtMail.getValue().toString().trim());
			
			delegadoDeNegocio.modificarClientes(clientes);  
			
			losClientes = delegadoDeNegocio.consultarTodosClientes();
			
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El cliente fue modificado con exito",""));
			

		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}
	
	public String borrarAction(){
		try{
			Clientes clientes= new Clientes();
			
			
			clientes.setCliId( Long.parseLong(   txtIdentificacion.getValue().toString().trim()  ));
			
			delegadoDeNegocio.borrarClientes(clientes);
			
			limpiarAction();
			
			losClientes = delegadoDeNegocio.consultarTodosClientes();
			
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El cliente fue borrado con exito",""));
			

		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}
	public String limpiarAction(){
		try{
			txtIdentificacion.resetValue();
			txtDireccion.resetValue();
			txtMail.resetValue();
			txtNombre.resetValue();
			txtTelefono.resetValue();
			somTipoDocumento.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);

		}catch(Exception e){
			log.error( e.getMessage());
		}
		return null;
	}











	public List<Clientes> getLosClientes() {

		return losClientes;
	}

	public void setLosClientes(List<Clientes> losClientes) {
		this.losClientes = losClientes;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<SelectItem> getLosTiposDocumentos() {
		return losTiposDocumentos;
	}

	public void setLosTiposDocumentos(List<SelectItem> losTiposDocumentos) {
		this.losTiposDocumentos = losTiposDocumentos;
	}




	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}




	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}




	public InputText getTxtNombre() {
		return txtNombre;
	}




	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}




	public InputText getTxtDireccion() {
		return txtDireccion;
	}




	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}




	public InputText getTxtTelefono() {
		return txtTelefono;
	}




	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}




	public InputText getTxtMail() {
		return txtMail;
	}




	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
	}




	public SelectOneMenu getSomTipoDocumento() {
		return somTipoDocumento;
	}




	public void setSomTipoDocumento(SelectOneMenu somTipoDocumento) {
		this.somTipoDocumento = somTipoDocumento;
	}




	public CommandButton getBtnCrear() {
		return btnCrear;
	}




	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}




	public CommandButton getBtnModificar() {
		return btnModificar;
	}




	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}




	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}




	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}




	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}




	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}







}
