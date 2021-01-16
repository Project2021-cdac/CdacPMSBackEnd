package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Guide;
import com.cpms.pojos.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	
	@Query("SELECT new com.cpms.pojos.UserAccount(email,password) FROM UserAccount u JOIN Student s ON s.user.id=u.id")
	List<UserAccount> getStudentUserAccountforRegisteration();
	
	@Query("SELECT new com.cpms.pojos.UserAccount(firstName, lastName, role, email, phoneNumber, dateOfBirth, courseName) "
	+ "from UserAccount u JOIN Guide g ON g.user.id=u.id")
	List<Guide> getUserAccountofGuides();

}
