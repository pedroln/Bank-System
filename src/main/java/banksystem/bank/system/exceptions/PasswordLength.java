package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordLength {
   @ExceptionHandler(value = PasswordLengthException.class)
   public ResponseEntity<Object> exception(PasswordLengthException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("erro:", "tamanho da senha é menor do que o recomendado (8 caracteres)o");
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}