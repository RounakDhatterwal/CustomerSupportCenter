package com.supportcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supportcenter.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer>{

	public Operator findByEmail(String email);
	
	
	
	
}
