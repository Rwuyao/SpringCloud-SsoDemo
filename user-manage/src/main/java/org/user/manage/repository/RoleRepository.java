package org.user.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.manage.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);
}
