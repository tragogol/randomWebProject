package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
