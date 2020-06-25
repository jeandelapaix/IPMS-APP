package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>
{
    Role findByName(String role);
}