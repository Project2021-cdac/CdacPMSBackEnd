package com.cpms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.UserAccount;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Integer> {

	Optional<Guide> findByUserAccount(UserAccount userAccount);
	
	@Query("select g from Guide g join fetch g.userAccount u where u.courseName = :course")
	List<Guide> findByCourse(@Param("course") Course course);

}
