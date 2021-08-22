package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InsuficientBalance {
   @ExceptionHandler(value = InsuficientBalanceException.class)
   public ResponseEntity<Object> exception(InsuficientBalanceException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
      responseMessage.put("erro:", "saldo insuficiente para transferência"); 
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}