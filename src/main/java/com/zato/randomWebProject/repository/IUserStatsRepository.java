package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.User;
import com.zato.randomWebProject.data.UserStats;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserStatsRepository {//extends CrudRepository<UserStats, Long> {
    List<UserStats> findById(long id);
    List<UserStats> findByUser_id(long id);

}
