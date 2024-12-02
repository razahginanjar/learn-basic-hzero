package com.hand.demo.infra.mapper;

import com.hand.demo.api.dto.TaskDTO;
import com.hand.demo.domain.entity.Task;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 查询任务
     *
     * @param params 任务查询参数
     * @return Task
     */
    List<Task> selectTask(Task params);
    List<TaskDTO> selectList(TaskDTO params);
}
