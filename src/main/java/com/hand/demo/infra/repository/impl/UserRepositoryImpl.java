package com.hand.demo.infra.repository.impl;

import com.hand.demo.domain.entity.User;
import com.hand.demo.domain.repository.UserRepository;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
}
