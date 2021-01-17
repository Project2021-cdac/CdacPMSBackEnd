package com.cpms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Admin;
import com.cpms.pojos.UserAccount;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByUserAccount(UserAccount user);
}
