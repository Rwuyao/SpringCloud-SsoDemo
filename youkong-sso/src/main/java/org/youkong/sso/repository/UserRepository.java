package org.youkong.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youkong.sso.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
		public User findByName(String name);
}
