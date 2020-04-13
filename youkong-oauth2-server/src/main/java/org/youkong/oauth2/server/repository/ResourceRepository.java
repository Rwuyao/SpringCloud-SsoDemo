package org.youkong.oauth2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.oauth2.server.entity.Resource;



public interface ResourceRepository extends JpaRepository<Resource, Long> {
	public Resource findByName(String name);
}
