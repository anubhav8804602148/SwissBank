package com.cs.swiss;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.id=?1")
	public List<User> findById(int id);

	@Query("select u from User u where u.email=?1")
	public List<User> findByEmail(String mail);
	
	
	
}
