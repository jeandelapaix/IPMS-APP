package rw.imps.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
	
	User findByUsername(String userName);
	
}