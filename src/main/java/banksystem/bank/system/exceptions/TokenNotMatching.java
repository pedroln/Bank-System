package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenNotMatching {
   @ExceptionHandler(value = TokenNotMatchingException.class)
   public ResponseEntity<Object> exception(TokenNotMatchingException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("message:", "Acesso Negado");
      return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
   }
   
}