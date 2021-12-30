package com.cs.swiss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select t from Transaction t where t.accountNumber=?1 or t.fromAccountNumber=?1")
	public List<Transaction> findByAccountNumber(long accNum);

	@Query("select t from Transaction t where t.id=?1")
	public List<Transaction> findById(long id);
}
