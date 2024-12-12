package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.DemoRedisDTO;
import com.hand.demo.api.dto.TaskDTO;
import com.hand.demo.api.dto.UserDTO;
import com.hand.demo.app.service.UserService;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.entity.User;
import com.hand.demo.domain.repository.TaskRepository;
import com.hand.demo.domain.repository.UserRepository;
import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.hzero.common.HZeroCacheKey;
import org.hzero.core.cache.Cacheable;
import org.hzero.core.cache.ProcessCacheValue;
import org.hzero.core.redis.RedisHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RedisHelper redisHelper;

    public UserServiceImpl(TaskRepository taskRepository, UserRepository userRepository, RedisHelper redisHelper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.redisHelper = redisHelper;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    @ProcessCacheValue
    public User create(User user) {
        userRepository.insert(user);
        return user;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) {
        User exist = userRepository.selectByPrimaryKey(userId);
        if (exist == null) {
            throw new CommonException("htdo.warn.user.notFound");
        }
        // 删除用户
        userRepository.deleteByPrimaryKey(userId);
        // 删除与用户关联的任务
        List<Task> tasks = taskRepository.selectByEmployeeId(userId);
        if (CollectionUtils.isNotEmpty(tasks)) {
            taskRepository.batchDelete(tasks);
        }
    }

    @Override
    @ProcessCacheValue
    public List<UserDTO> exportData(UserDTO userDTO) {
        String s1 = redisHelper.strGet(HZeroCacheKey.USER+":2302");
        log.info("created_by:{}", s1);

        String s2 = redisHelper.hshGet("2302", HZeroCacheKey.USER);
        log.info(s2);

        List<UserDTO> userDTOS = userRepository.selectList(userDTO);
        List<Long> ids = new ArrayList<>();
        userDTOS.forEach(userDTO1 -> ids.add(userDTO1.getId()));
        Map<Long, List<TaskDTO>> maps = taskRepository
                .selectList( new TaskDTO().setEmpIdList(ids)).stream().collect(
                        Collectors.groupingBy(TaskDTO::getEmployeeId)
                );
        userDTOS.forEach(userDTO1 -> userDTO1.setTasks(maps.get(userDTO1.getId())));

        String s = redisHelper.strGet("realName");
        log.info( "this is info of {}", s);

        return userDTOS;
    }

}
