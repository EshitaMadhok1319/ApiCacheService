package com.cache.service.cacheRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cache.service.Dao.Employees;

/*
 * This class is extending JPA to help use the basic DB commands
 * 
 * Developer: Eshita Madhok
 * 
 */

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String>{

}
