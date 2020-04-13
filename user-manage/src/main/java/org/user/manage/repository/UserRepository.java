package org.user.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.manage.entity.BaseUser;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
		public BaseUser findByName(String name);
}
