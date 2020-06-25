package rw.imps.service;

import rw.imps.domain.Role;

import java.util.List;

public interface RoleService {
	public final static String NAME = "RoleServiceImpl";

	Role createRole(Role role);

	Role findByName(String name);

	List<Role> findAll();

	Role update(Role role);

	void deleteRole(long roleId);
}
