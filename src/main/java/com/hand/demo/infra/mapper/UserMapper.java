package com.hand.demo.infra.mapper;

import com.hand.demo.domain.entity.User;
import io.choerodon.mybatis.common.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
