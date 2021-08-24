package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountNotLogged {
   @ExceptionHandler(value = AccountNotLoggedException.class)
   public ResponseEntity<Object> exception(AccountNotLoggedException exception) {
	  HashMap<String, String> responseMessage = new HashMap<>();
	  responseMessage.put("erro:", "conta de destino não pertence ao usuário logado");
      return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
   }
   
}

