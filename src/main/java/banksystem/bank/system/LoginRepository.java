/**
Author - Pedro de Oliveira Lima Nunes
*/

package banksystem.bank.system;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoggedUser, Long> {

}