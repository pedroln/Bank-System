package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExistingAccountNumber {
   @ExceptionHandler(value = ExistingAccountNumberException.class)
   public ResponseEntity<Object> exception(ExistingAccountNumberException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("erro:", "já existe uma conta com o número informado");
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}