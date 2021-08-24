/**
Author - Pedro de Oliveira Lima Nunes
*/

package banksystem.bank.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import banksystem.bank.system.config.JWTAuthorizationFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.GET, "/users").permitAll()
					.antMatchers(HttpMethod.POST, "/users").permitAll().antMatchers(HttpMethod.POST, "/auth")
					.permitAll().antMatchers(HttpMethod.GET, "/logged").permitAll()
					.antMatchers(HttpMethod.GET, "/accountlist").permitAll().anyRequest().authenticated();
		}
	}

}
