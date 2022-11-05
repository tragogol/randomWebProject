package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
