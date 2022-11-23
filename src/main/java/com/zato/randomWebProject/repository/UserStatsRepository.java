package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
}
