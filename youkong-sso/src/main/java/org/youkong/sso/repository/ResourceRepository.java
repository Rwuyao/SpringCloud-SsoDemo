package org.youkong.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.sso.entity.Resource;
import org.youkong.sso.entity.User;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	public Resource findByName(String name);
}
