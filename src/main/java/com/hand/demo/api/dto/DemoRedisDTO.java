package com.hand.demo.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.hzero.common.HZeroCacheKey;
import org.hzero.core.cache.CacheValue;
import org.hzero.core.cache.Cacheable;

@Getter
@Setter
public class DemoRedisDTO implements Cacheable {
    private Long createdBy; // 创建人ID
    @CacheValue(key = HZeroCacheKey.USER, primaryKey = "createdBy", searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT)
    private String createdUserName; // 创建人姓名
}
