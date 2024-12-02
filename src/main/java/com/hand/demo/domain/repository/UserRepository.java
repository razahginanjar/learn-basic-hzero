package com.hand.demo.domain.repository;

import com.hand.demo.api.dto.UserDTO;
import com.hand.demo.domain.entity.User;
import org.hzero.mybatis.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByUserAccount(Long userAccount);
    List<UserDTO> selectList(UserDTO userDTO);
}
