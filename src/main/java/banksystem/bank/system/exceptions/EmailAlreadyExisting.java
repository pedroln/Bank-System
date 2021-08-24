package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailAlreadyExisting {
	@ExceptionHandler(value = ExistingEmailException.class)
	public ResponseEntity<Object> exception(ExistingEmailException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "já existe um usuário com o email informado");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}
}