package com.hand.demo.api.controller.v1;

import com.hand.demo.app.service.TaskService;
import com.hand.demo.config.SwaggerTags;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.repository.TaskRepository;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.*;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = SwaggerTags.TASK)
@RestController("taskController.v1")
@RequestMapping("/v1/ra/{organizationId}/tasks")
public class TaskController extends BaseController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }
    /**
     * 注意分页参数是 io.choerodon.mybatis.pagehelper.domain.PageRequest;
     */
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber分页查询task")
    @GetMapping
    public ResponseEntity<Page<Task>> list(@PathVariable("organizationId") Long tenantId,
                                           @RequestBody Task task, PageRequest pageRequest) {
        task.setTenantId(tenantId);
        return Results.success(taskRepository.pageTask(task, pageRequest));
    }
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "创建task")
    @PostMapping
    public ResponseEntity<Task> create(@PathVariable("organizationId") Long tenantId, @RequestBody Task task) {
        task.setTenantId(tenantId);
        // 简单数据校验
        this.validObject(task);
        return Results.success(taskService.create(task));
    }
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "更新task")
    @PutMapping
    public ResponseEntity<Task> update(@PathVariable("organizationId") Long tenantId,
                                       @RequestBody Task task) {
        // 简单数据校验
        this.validObject(task);
        // 数据防篡改校验
        SecurityTokenHelper.validToken(task);
        return Results.success(taskService.update(task));
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber查询task")
//    @ApiImplicitParams({
////            @ApiImplicitParam(value = "任务编号", paramType = "string")
//    })

    @GetMapping("/{taskNumber}")
    public ResponseEntity<Task> query(@PathVariable Long organizationId,
                                      @PathVariable String taskNumber) {
        return Results.success(taskRepository.selectDetailByTaskNumber(taskNumber));
    }
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber删除task")
    @DeleteMapping("/{taskNumber}")
    public void delete(@PathVariable Long organizationId, @PathVariable @ApiParam(
            value = "任务编号") String taskNumber) {
        taskService.deleteByTaskNumber(taskNumber);
    }


    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber分页查询task")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Task>> listAll(@PathVariable("organizationId") Long tenantId) {
        return Results.success(taskRepository.selectAll());
    }
}
