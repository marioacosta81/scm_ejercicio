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
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean
public class UsuarioVista {

	private static final Logger log=LoggerFactory.getLogger(UsuarioVista.class);

	@ManagedProperty(value="#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;

	List<Usuarios> losUsuarios;

	List<SelectItem> losTiposUsuarios;


	private InputText txtCedula;
	private InputText txtNombre;
	private InputText txtLogin;
	private InputText txtClave;

	private SelectOneMenu somTipoUsuario;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;





	@PostConstruct
	public void ini(){
		try{

			//consultar grilla usuarios
			losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();  


			//consultar tipos de usuarios
			losTiposUsuarios = new ArrayList<SelectItem>();
			List<TiposUsuarios> listTiposUsuarios = delegadoDeNegocio.consultarTodosTiposUsuarios();
			for(TiposUsuarios tu:listTiposUsuarios){
				SelectItem si = new SelectItem(tu.getTusuCodigo(), tu.getTusuNombre());
				losTiposUsuarios.add(si);
			}

		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}







	public void txtIdentificacionListener(){
		Usuarios entity=null;


		try {
			Long id=Long.parseLong( txtCedula.getValue().toString().trim());
			entity=delegadoDeNegocio.consultarUsuarioPorId(id);
		} catch (Exception e) {
		}

		if(entity==null){
			
			txtLogin.resetValue();
			txtNombre.resetValue();
			txtClave.resetValue();
			somTipoUsuario.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);
		}else{

			txtNombre.setValue(entity.getUsuNombre());
			txtLogin.setValue(entity.getUsuLogin());
			txtClave.setValue( entity.getUsuClave()  );

			somTipoUsuario.setValue(entity.getTiposUsuarios().getTusuCodigo());

			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);

		}

	}



	public String crearAction(){
		try{
			Usuarios usuario= new Usuarios();


			usuario.setUsuCedula(Long.parseLong(   txtCedula.getValue().toString().trim()  ));


			TiposUsuarios tiposUsuarios = delegadoDeNegocio.
					consultarTipoUsuarioPorId(
							Long.parseLong(   somTipoUsuario.getValue().toString().trim())   );
			usuario.setTiposUsuarios(tiposUsuarios);



			usuario.setUsuLogin(  txtLogin.getValue().toString().trim()  ); 
			usuario.setUsuNombre( txtNombre.getValue().toString().trim() ); 
			usuario.setUsuClave(txtClave.getValue().toString().trim());

			delegadoDeNegocio.grabarUsuarios(usuario);

			losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();

			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El usuario fue grabado con exito",""));


		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}

	public String modificarAction(){
		try{
			Usuarios usuario= new Usuarios();


			usuario.setUsuCedula(Long.parseLong(   txtCedula.getValue().toString().trim()  ));


			TiposUsuarios tiposUsuarios = delegadoDeNegocio.
					consultarTipoUsuarioPorId(
							Long.parseLong(   somTipoUsuario.getValue().toString().trim())   );
			usuario.setTiposUsuarios(tiposUsuarios);



			usuario.setUsuLogin(  txtLogin.getValue().toString().trim()  ); 
			usuario.setUsuNombre( txtNombre.getValue().toString().trim() ); 
			usuario.setUsuClave(txtClave.getValue().toString().trim());

			delegadoDeNegocio.modificarUsuarios(usuario);

			losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();

			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El usuario fue modificado con exito",""));


		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}

	public String borrarAction(){
		try{
			Usuarios usuario= new Usuarios();


			usuario.setUsuCedula(Long.parseLong(   txtCedula.getValue().toString().trim()  ));


			delegadoDeNegocio.borrarUsuarios(usuario); 

			losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
			
			limpiarAction();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,"El usuario fue borrado con exito",""));


		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}



	public String limpiarAction(){
		try{
			txtCedula.resetValue();
			txtLogin.resetValue();
			txtNombre.resetValue();
			txtClave.resetValue();
			somTipoUsuario.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);

		}catch(Exception e){
			log.error( e.getMessage());
		}
		return null;
	}


















	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}





	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}





	public List<Usuarios> getLosUsuarios() {
		return losUsuarios;
	}





	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}





	public List<SelectItem> getLosTiposUsuarios() {
		return losTiposUsuarios;
	}





	public void setLosTiposUsuarios(List<SelectItem> losTiposUsuarios) {
		this.losTiposUsuarios = losTiposUsuarios;
	}





	public InputText getTxtCedula() {
		return txtCedula;
	}





	public void setTxtCedula(InputText txtCedula) {
		this.txtCedula = txtCedula;
	}





	public InputText getTxtNombre() {
		return txtNombre;
	}





	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}





	public InputText getTxtLogin() {
		return txtLogin;
	}





	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}





	public SelectOneMenu getSomTipoUsuario() {
		return somTipoUsuario;
	}





	public void setSomTipoUsuario(SelectOneMenu somTipoUsuario) {
		this.somTipoUsuario = somTipoUsuario;
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







	public InputText getTxtClave() {
		return txtClave;
	}







	public void setTxtClave(InputText txtClave) {
		this.txtClave = txtClave;
	}













}
