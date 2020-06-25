package rw.imps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.*;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long>
{
    Iterable<UserRole> findByRole(Role userRole);
    boolean findByUserAndRole(User user, Role role);
}