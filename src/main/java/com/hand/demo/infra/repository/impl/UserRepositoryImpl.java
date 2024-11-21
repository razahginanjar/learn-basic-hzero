package com.hand.demo.infra.repository.impl;

import com.hand.demo.domain.entity.User;
import com.hand.demo.domain.repository.UserRepository;
import com.hand.demo.infra.mapper.UserMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserAccount(Long userAccount) {
        return userMapper.selectByUserAccount(userAccount);
    }
}
