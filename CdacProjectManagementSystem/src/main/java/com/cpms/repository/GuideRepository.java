package com.cpms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Guide;
import com.cpms.pojos.UserAccount;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Integer> {

	Guide findByUserAccount(UserAccount userAccount);

}
