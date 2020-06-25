package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.User;

@Repository
public interface UserDaoc extends JpaRepository<User, Long> {
	User findByUsername(String userName);

	User findByEmail(String email);

	User findById(long id);
	
}