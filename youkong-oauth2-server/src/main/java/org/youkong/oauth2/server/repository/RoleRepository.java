package org.youkong.oauth2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.oauth2.server.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);
}
