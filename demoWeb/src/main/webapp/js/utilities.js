/**
 * funci�n que permite ingresar solo digitos n�mericos en 
 * un campo de texto
 * @autor macosta 07/05/2015
 * @param {Object} e
 * @return {TypeName} 
 */
function validarNumerico(e) {
	var tecla = (document.all) ? e.keyCode : e.which;
	if (tecla > 47 && tecla < 58 || tecla == 8 || tecla == 0) {
		return true;
	} else
		return false;
}
