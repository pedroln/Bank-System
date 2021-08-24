package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SourceAccountNotFound {
	@ExceptionHandler(value = SourceAccountNotFoundException.class)
	public ResponseEntity<Object> exception(SourceAccountNotFoundException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "Conta de Origem não encontrada para o usuário");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

}