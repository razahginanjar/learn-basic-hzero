package com.hand.demo.domain.repository;

import com.hand.demo.domain.entity.User;
import org.hzero.mybatis.base.BaseRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByUserAccount(Long userAccount);
}
