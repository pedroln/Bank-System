package banksystem.bank.system.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import banksystem.bank.system.User;
import banksystem.bank.system.UserRepository;
import banksystem.bank.system.exceptions.BlankEmailFieldException;
import banksystem.bank.system.exceptions.BlankNameFieldException;
import banksystem.bank.system.exceptions.BlankPasswordFieldException;
import banksystem.bank.system.exceptions.ExistingEmailException;
import banksystem.bank.system.exceptions.FieldNotOnBodyException;
import banksystem.bank.system.exceptions.InvalidEmailException;
import banksystem.bank.system.exceptions.PasswordLengthException;

@RestController
class UserController {
	
	
  User user = new User();

  private final UserRepository repository;
  
  

  UserController(UserRepository repository) {
    this.repository = repository;
      
  }
  
  @GetMapping("/users")
  List<User> all() {
    return repository.findAll();
  }
  
  @PostMapping("/users")
  ResponseEntity<Object> newUser(@RequestBody User newUser) {
	
	  List<User> users = repository.findAll();
	  HashMap<String, String> responseMessage = new HashMap<>();
	  
	  
	  if (newUser.getEmail() == null || newUser.getName() == null || newUser.getPassword() == null) {
		  throw new FieldNotOnBodyException();
	  }
	  
	  
	  else {
		  
		  String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		  Boolean validEmail = newUser.getEmail().matches(EMAIL_REGEX);
		  
		  if (newUser.getEmail().isBlank()) {
			  throw new BlankEmailFieldException();  	  
		  }
		  
		  
		  else if (validEmail == false) {
			  
			  throw new InvalidEmailException();
		  }
		  
		  else if (newUser.getName().isBlank()) {
			  throw new BlankNameFieldException();
		  }
		 
		  else if (newUser.getPassword().isBlank()) {
			  throw new BlankPasswordFieldException();
		  }
		  
		  else if (newUser.getPassword().length() < 8) {
			  throw new PasswordLengthException();
		  }
		  
		  else {
		  // checando se o email de cadastro já foi utilizado
		  for (User user : users) {
			  if (user.getEmail().equals(newUser.getEmail())){
				  throw new ExistingEmailException();
			  }  
		  	}
		  }
	  }
	  
	  // adicionando o novo usuário no banco de dados persistente
	  repository.save(newUser);
	  
	  // criando um novo JSON para guardar apenas as informações de retorno necessários (email e name)
	  
	  responseMessage.put("email", newUser.getEmail());
	  responseMessage.put("name", newUser.getName());
	  return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	
	
	
  }
  
  

 
  
 

  
}