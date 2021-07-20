package com.database.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.database.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	  @Query("SELECT u FROM User u WHERE u.email = :email")
	  public User getUserByEmail(@Param("email") String username);
	  
	  @Query("SELECT u FROM User u WHERE u.VerificationCode = ?1")
	  public User getUserByVerficationCode(@Param("email") String code);
	  
	  @Query("SELECT u FROM User u WHERE u.resetPasswordToken = ?1")
	  public User getUserByResetPasswordToken(@Param("resettoken") String code);
	
	
	
	

}
