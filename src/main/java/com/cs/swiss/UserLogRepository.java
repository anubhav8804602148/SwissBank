package com.cs.swiss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLogRepository extends JpaRepository<UserLog, String>{
	
	@Query("SELECT UL from UserLog UL where UL.email=?1")
	public List<UserLog> findUserLogByEmail(String email);
}
