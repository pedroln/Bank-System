package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FieldNotOnBody {
	@ExceptionHandler(value = FieldNotOnBodyException.class)
	public ResponseEntity<Object> exception(FieldNotOnBodyException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "algum dos campos necessários não se encontram na requisição");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

}