package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlankNameField {
   
   @ExceptionHandler(value = BlankNameFieldException.class)
   public ResponseEntity<Object> exception(BlankNameFieldException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("erro:", "campo name sem informações inseridas");
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}