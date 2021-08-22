package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotMatchingCredentials {
   @ExceptionHandler(value = NotMatchingCredentialsException.class)
   public ResponseEntity<Object> exception(NotMatchingCredentialsException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("erro:", "credenciais enviadas não encontradas no banco de dados");
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}