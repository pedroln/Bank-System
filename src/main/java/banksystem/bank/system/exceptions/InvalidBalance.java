package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidBalance {
	@ExceptionHandler(value = InvalidBalanceException.class)
	public ResponseEntity<Object> exception(InvalidBalanceException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "saldo deve ser maior ou igual a zero");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

}
