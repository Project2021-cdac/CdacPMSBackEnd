package com.cpms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer>{
	UserAccount findByEmailAndPassword(String email, String password);
	
	Optional<UserAccount> findByEmail(String email);
	
	@Query("UPDATE UserAccount user SET user.password =:pass WHERE user.id =:id ")
	UserAccount changePassword(@Param("pass") String updatePassword, @Param("id")int userId);
}
