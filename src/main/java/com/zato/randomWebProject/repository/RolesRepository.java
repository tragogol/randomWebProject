package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
