package org.user.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.manage.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	public Resource findByName(String name);
}
