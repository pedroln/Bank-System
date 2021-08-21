package banksystem.bank.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import banksystem.bank.system.LoggedUser;
import banksystem.bank.system.User;
import banksystem.bank.system.UserRepository;
import banksystem.bank.system.exceptions.BlankEmailFieldException;
import banksystem.bank.system.exceptions.BlankNameFieldException;
import banksystem.bank.system.exceptions.BlankPasswordFieldException;
import banksystem.bank.system.exceptions.ExistingEmailException;
import banksystem.bank.system.exceptions.InvalidEmailException;
import banksystem.bank.system.exceptions.PasswordLengthException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
  
  @PostMapping("/auth")
	public LoggedUser login(@RequestBody User newUser) {
	  List<User> users = repository.findAll();
	  
	  
	  for (User user : users) {
		  // checa se email e password são correspondentes pra o que tá dentro da database
		  if (user.getEmail().equals(newUser.getEmail())){
			  if (user.getPassword().equals(newUser.getPassword())){
				  String token = getJWTToken(newUser.getEmail());
				  LoggedUser loggedUser = new LoggedUser();
				  loggedUser.setEmail(user.getEmail());
				  loggedUser.setName(user.getName());
				  loggedUser.setToken(token);	
				  return loggedUser;
					
			  }
		  } 
	  }
	  throw new BlankEmailFieldException();  
  }
  
  private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}


  @PostMapping("/users")
  ResponseEntity<Object> newUser(@RequestBody User newUser) {
	
	  List<User> users = repository.findAll();
	  HashMap<String, String> responseMessage = new HashMap<>();
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
	  
	  // adicionando o novo usuário no banco de dados persistente
	  repository.save(newUser);
	  
	  // criando um novo JSON para guardar apenas as informações de retorno necessários (email e name)
	  
	  responseMessage.put("email", newUser.getEmail());
	  responseMessage.put("name", newUser.getName());
	  return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	
	
	
  }
  
  

 
  
 

  
}