package com.brownie.userman;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brownie.userman.User;

@Repository
public interface UserRepository extends JpaRepository < User, Long > {
	
	Optional<User> findById(Long id);
	User findByUserName(String username);
    User findByEmail(String email);
    
    List<User> findAll();
    
    List<User> findAllByOrderByUserNameAsc();
    
    
	// Extra
	// by WRT
    @Modifying
    @Transactional
	@Query(value = "UPDATE user " +
	               "SET enabled = 1 " +
	               "WHERE id = ?1", 
			nativeQuery=true
	)
	void enabledUser(Long id);
    
	// Extra
	// by WRT
    @Modifying
    @Transactional
	@Query(value = "UPDATE user " +
	               "SET enabled = 0 " +
	               "WHERE id = ?1", 
			nativeQuery=true
	)
	void disabledUser(Long id);
	
    
}