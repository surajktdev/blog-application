package com.tech.blog.repository;

import com.tech.blog.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM `blog-app-apis`.user where email = :email")
	Optional<User> findByEmail(String email);

	//	Optional<User> findByEmail(String email);
}
