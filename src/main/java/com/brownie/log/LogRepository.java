package com.brownie.log;

import org.springframework.data.repository.CrudRepository;

import com.brownie.userman.User;

import java.util.List;
import org.springframework.data.domain.Sort;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface LogRepository extends CrudRepository<Log, Long> {

	List<Log> findAll();
	
	List<Log> findAllByOrderByIdDesc();

}