package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlankEmailField {
	@ExceptionHandler(value = BlankEmailFieldException.class)
	public ResponseEntity<Object> exception(BlankEmailFieldException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "campo email sem informações inseridas");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

}