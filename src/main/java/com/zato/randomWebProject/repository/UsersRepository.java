package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
