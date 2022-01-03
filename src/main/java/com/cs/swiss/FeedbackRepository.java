package com.cs.swiss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
	
	@Query("select f from Feedback f where f.id=?1")
	public List<Feedback> findFeedbackById(int id);
	
}
