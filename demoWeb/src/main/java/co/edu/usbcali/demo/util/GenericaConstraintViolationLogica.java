package co.edu.usbcali.demo.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericaConstraintViolationLogica<T> {
	private Logger log=LoggerFactory.getLogger(GenericaConstraintViolationLogica.class);
	
	
	private Validator validator;
	
	private T validado;

	public GenericaConstraintViolationLogica(T validado,Validator validator){
		this.validado = validado;
		this.validator = validator;
	}


	public StringBuilder getConstrains() throws Exception {

		StringBuilder stringBuilder=new StringBuilder();

		Set<ConstraintViolation<T>> constraintViolations=validator.validate(validado);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			return stringBuilder;
		}		
		return null;

	}


}
