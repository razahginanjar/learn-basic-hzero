package com.hand.demo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import com.hand.demo.domain.entity.InvCountHeader;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * Inventory Count Header Table(InvCountHeader)应用服务
 *
 * @author razah
 * @since 2024-11-28 10:08:41
 */
public interface InvCountHeaderMapper extends BaseMapper<InvCountHeader> {
    /**
     * 基础查询
     *
     * @param invCountHeader 查询条件
     * @return 返回值
     */
    List<InvCountHeader> selectList(InvCountHeader invCountHeader);

    InvCountHeader findByCountNumber(@Param(value = "countNumber") String countNumber);
}

