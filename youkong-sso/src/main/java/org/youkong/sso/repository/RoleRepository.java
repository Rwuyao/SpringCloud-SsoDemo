package org.youkong.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.sso.entity.Role;
import org.youkong.sso.entity.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);
}
