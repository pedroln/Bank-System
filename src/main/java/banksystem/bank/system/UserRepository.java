/**
Author - Pedro de Oliveira Lima Nunes
*/

package banksystem.bank.system;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}