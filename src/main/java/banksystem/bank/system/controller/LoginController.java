package banksystem.bank.system.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import banksystem.bank.system.LoggedUser;
import banksystem.bank.system.LoginRepository;
import banksystem.bank.system.User;
import banksystem.bank.system.UserRepository;
import banksystem.bank.system.exceptions.BlankEmailFieldException;
import banksystem.bank.system.exceptions.NotMatchingCredentialsException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {
	
	User user = new User();

	  private final UserRepository repository;
	  private final LoginRepository loginRepository;
	  
	  LoginController(UserRepository repository, LoginRepository loginRepository) {
	    this.repository = repository;
	    this.loginRepository = loginRepository;
	    
	    
	  }
	@GetMapping("/logged")
	 List<LoggedUser> all() {
	    return loginRepository.findAll();
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
				  // salva o usuario logado com suas informações de nome email e token
				  loginRepository.save(loggedUser);
				  return loggedUser;
					
			  }
		  } 
	  }
	  throw new NotMatchingCredentialsException();  
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

		return token;
	}

}
