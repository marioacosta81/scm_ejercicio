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
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.logica.transacciones.ITransaccionesLogica;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean
public class TransaccionesVista {

	private static final Logger log=LoggerFactory.getLogger(TransaccionesVista.class);

	@ManagedProperty(value="#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;
	
	@ManagedProperty(value="#{transaccionesLogica}")
	ITransaccionesLogica transaccionesLogica;
	


	List<SelectItem> losUsuarios;


	private InputText txtNumeroCuenta;
	private InputText txtCliente;
	private InputText    txtValor;
	private Password txtClave;

	private SelectOneMenu somUsuario;

	private CommandButton btnConsignar;
	private CommandButton btnRetirar;
	private CommandButton btnLimpiar;

	private Cuentas cuenta;
	
	
	private InputText    txtNuevoSaldo;
	private InputText    txtSaldoAnterior;



	@PostConstruct
	public void ini(){
		try{

			
			
			//consultar tipos de usuarios
			losUsuarios = new ArrayList<SelectItem>();
			List<Usuarios> listUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
			for(Usuarios u:listUsuarios){
				SelectItem si = new SelectItem(u.getUsuCedula(),u.getUsuNombre());
				losUsuarios.add(si);
			}

		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}







	public void txtNumeroCuentaListener(){



		try {
			String  numeroCuenta=txtNumeroCuenta.getValue().toString().trim();
			cuenta=delegadoDeNegocio.consultarCuentasPorId(numeroCuenta);
			
			


			if(cuenta==null){

				txtCliente.resetValue();
				txtNuevoSaldo.resetValue();
				btnConsignar.setDisabled(true);
				btnRetirar.setDisabled(true);
				btnLimpiar.setDisabled(false);
			}else{

				txtCliente.setValue(cuenta.getClientes().getCliNombre());
				txtNuevoSaldo.setValue(cuenta.getCueSaldo());

				btnConsignar.setDisabled(false);
				btnRetirar.setDisabled(false);
				btnLimpiar.setDisabled(false);

			}

			txtClave.resetValue();
			somUsuario.setValue("-1");
			txtValor.resetValue();
		} catch (Exception e) {
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}

	}



	public String consignarAction(){
		try{
			
			Usuarios usuario = delegadoDeNegocio.consultarUsuarioPorId(
				Long.parseLong(	somUsuario.getValue().toString().trim()));
			
			//validar usuario
			if(usuario == null){
				throw new Exception("Debe ingresar usuario");
			}
			
			//validar valor
			if(txtValor.getValue().toString().trim()==null ||  
				txtValor.getValue().toString().trim().isEmpty()){
				
				throw new Exception("Debe ingresar un valor");
			}
			
			//validar clave
			if(txtClave  .getValue().toString().trim()==null ||  
					txtClave.getValue().toString().trim().isEmpty()){
					
					throw new Exception("Debe ingresar la clave personal");
				}
			
			
			
			transaccionesLogica.consignar(
					cuenta.getCueNumero(),
					usuario.getUsuCedula(),
					Long.parseLong( txtValor.getValue().toString().trim()),
					txtClave.getValue().toString().trim()
				);
			
			String  numeroCuenta=txtNumeroCuenta.getValue().toString().trim();
			cuenta=delegadoDeNegocio.consultarCuentasPorId(numeroCuenta);
			
			
			txtSaldoAnterior.setValue(txtNuevoSaldo.getValue().toString().trim()); 
			txtNuevoSaldo.setValue(cuenta.getCueSaldo());
			

			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,
							"La consignación se ha realizado con éxito",""));


		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}

	public String retirarAction(){
		try{
			Usuarios usuario = delegadoDeNegocio.consultarUsuarioPorId(
					Long.parseLong(	somUsuario.getValue().toString().trim()));
				
				//validar usuario
				if(usuario == null){
					throw new Exception("Debe ingresar usuario");
				}
				
				//validar valor
				if(txtValor.getValue().toString().trim()==null ||  
					txtValor.getValue().toString().trim().isEmpty()){
					
					throw new Exception("Debe ingresar un valor");
				}
				
				//validar clave
				if(txtClave  .getValue().toString().trim()==null ||  
						txtClave.getValue().toString().trim().isEmpty()){
						
						throw new Exception("Debe ingresar la clave personal");
					}
				
				
				
				transaccionesLogica.retirar(
						cuenta.getCueNumero(),
						usuario.getUsuCedula(),
						Long.parseLong( txtValor.getValue().toString().trim()),
						txtClave.getValue().toString().trim()
					);
				
				String  numeroCuenta=txtNumeroCuenta.getValue().toString().trim();
				cuenta=delegadoDeNegocio.consultarCuentasPorId(numeroCuenta);
				
				
				txtSaldoAnterior.setValue(txtNuevoSaldo.getValue().toString().trim()); 
				txtNuevoSaldo.setValue(cuenta.getCueSaldo());
				
				

				FacesContext.getCurrentInstance().addMessage("", 
						new FacesMessage( FacesMessage.SEVERITY_INFO,
								"El retiro se ha realizado con éxito",""));
		}catch(Exception e){
			log.error( e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage( FacesMessage.SEVERITY_INFO,e.getMessage(),""));
		}
		return null;
	}

	



	public String limpiarAction(){
		try{
			txtCliente.resetValue();
			txtClave.resetValue();
			somUsuario.setValue("-1");
			txtValor.resetValue();
			txtNumeroCuenta.resetValue();
			txtNuevoSaldo.resetValue();
			txtSaldoAnterior.resetValue();

			btnConsignar.setDisabled(true);
			btnRetirar.setDisabled(true);
			btnLimpiar.setDisabled(false);

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




	public Password getTxtClave() {
		return txtClave;
	}







	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}







	public ITransaccionesLogica getTransaccionesLogica() {
		return transaccionesLogica;
	}







	public void setTransaccionesLogica(ITransaccionesLogica transaccionesLogica) {
		this.transaccionesLogica = transaccionesLogica;
	}







	public List<SelectItem> getLosUsuarios() {
		return losUsuarios;
	}







	public void setLosUsuarios(List<SelectItem> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}







	public InputText getTxtNumeroCuenta() {
		return txtNumeroCuenta;
	}







	public void setTxtNumeroCuenta(InputText txtNumeroCuenta) {
		this.txtNumeroCuenta = txtNumeroCuenta;
	}







	public InputText getTxtCliente() {
		return txtCliente;
	}







	public void setTxtCliente(InputText txtCliente) {
		this.txtCliente = txtCliente;
	}







	public InputText getTxtValor() {
		return txtValor;
	}







	public void setTxtValor(InputText txtValor) {
		this.txtValor = txtValor;
	}







	public SelectOneMenu getSomUsuario() {
		return somUsuario;
	}







	public void setSomUsuario(SelectOneMenu somUsuario) {
		this.somUsuario = somUsuario;
	}







	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}







	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}







	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}







	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}







	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}







	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}







	public InputText getTxtNuevoSaldo() {
		return txtNuevoSaldo;
	}







	public void setTxtNuevoSaldo(InputText txtNuevoSaldo) {
		this.txtNuevoSaldo = txtNuevoSaldo;
	}







	public InputText getTxtSaldoAnterior() {
		return txtSaldoAnterior;
	}







	public void setTxtSaldoAnterior(InputText txtSaldoAnterior) {
		this.txtSaldoAnterior = txtSaldoAnterior;
	}













}
