package com.cs.swiss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("select a from Account a where a.account_number=?1")
	public List<Account> findByAccountNumber(long accNum);
	
	@Query("SELECT a FROM Account a WHERE a.userId=?1")
	public List<Account> findByUserId(String email);
}
