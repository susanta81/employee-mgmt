package com.suspro.employeemgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.suspro.employeemgmt.model.Employee;
import com.suspro.employeemgmt.model.User;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	/*@Query("select u from User u where u.emailId = :emailId")
	public User getUserByUserName(@Param("emailId") String emailId);*/
}
