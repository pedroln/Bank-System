package banksystem.bank.system.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DestinyAccountNotFound {
	@ExceptionHandler(value = DestinyAccountNotFoundException.class)
	public ResponseEntity<Object> exception(DestinyAccountNotFoundException exception) {
		HashMap<String, String> responseMessage = new HashMap<>();
		responseMessage.put("erro:", "Conta de Destino não encontrada para o usuário");
		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

}
