package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	
	// TODO check if this query is required
//	@Query("select s from Student")
//	List<Student> getStudentUserAccountforRegistration();
		
	//TODO In case change of package from pojos to models, change this query too
	@Query("select new com.cpms.pojos.UserAccount(firstName, lastName, email, phoneNumber, dateOfBirth, courseName) "
	+ "from UserAccount u where role='ROLE_GUIDE'")
	List<UserAccount> getUserAccountofGuides();

}
