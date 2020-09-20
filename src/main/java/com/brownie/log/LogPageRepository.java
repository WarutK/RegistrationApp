package com.brownie.log;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.brownie.userman.User;

import java.util.List;
import org.springframework.data.domain.Sort;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface LogPageRepository extends PagingAndSortingRepository<Log, Long> {



}