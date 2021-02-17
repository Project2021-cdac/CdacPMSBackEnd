package com.cpms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {	

}
