package com.cpms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer>{
	UserAccount findByEmailAndPassword(String email, String password);
	
	Optional<UserAccount> findByEmail(String email);
	
	@Query("SELECT admin FROM Admin admin JOIN FETCH admin.userAccount userAccount WHERE userAccount.id=:id")
	Admin findAdminByUserId(@Param("id")int userId);
	
	@Query("SELECT guide FROM Guide guide JOIN FETCH guide.userAccount userAccount WHERE userAccount.id=:id")
	Guide findGuideByUserId(@Param("id")int userId);
	
	@Query("SELECT student FROM Student student JOIN FETCH student.userAccount userAccount WHERE userAccount.id=:id")
	Student findStudentByUserId(@Param("id")int userId);
	
	//TODO In case change of package from pojos to models, change this query too
	@Query("UPDATE UserAccount userAccount SET userAccount.password =:pass WHERE userAccount.id =:id ")
	UserAccount changePassword(@Param("pass") String updatePassword, @Param("id")int userId);
}
