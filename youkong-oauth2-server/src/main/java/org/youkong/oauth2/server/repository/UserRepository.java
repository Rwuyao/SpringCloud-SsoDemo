package org.youkong.oauth2.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.oauth2.server.entity.BaseUser;


public interface UserRepository extends JpaRepository<BaseUser, Long> {
		public BaseUser findByName(String name);
}
