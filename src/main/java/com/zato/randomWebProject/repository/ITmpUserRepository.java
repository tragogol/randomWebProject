package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.TmpUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITmpUserRepository extends CrudRepository<TmpUser, Long> {

    TmpUser findTmpUserByLoginValue(String loginValue);
    TmpUser findTmpUserById(long id);
}
