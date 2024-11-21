package com.hand.demo.infra.mapper;

import com.hand.demo.domain.entity.User;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByUserAccount(@Param(value = "userAccount") Long userAccount);
}
