package com.hand.demo.api.controller.v1;

import com.hand.demo.api.dto.WorkFlowRequest;
import com.hand.demo.app.service.WorkFlowService;
import com.hand.demo.domain.entity.InvCountHeader;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.workflow.dto.PersonalTodoDTO;
import org.hzero.boot.workflow.dto.RunTaskHistory;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController("WorkFlowController.v1")
@RequestMapping("/v1/{organizationId}/workflow")
public class WorkFlowController extends BaseController {
    private final WorkFlowService workFlowService;


    public WorkFlowController(WorkFlowService workFlowService) {
        this.workFlowService = workFlowService;
    }

    @ApiOperation(value = "Start Work Flow")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<?> save(
            @PathVariable Long organizationId,
            @RequestBody WorkFlowRequest invCountHeaders) {
        validObject(invCountHeaders);
        workFlowService.start(organizationId, invCountHeaders);
        return Results.success("success");
    }


    @ApiOperation(value = "my todo list")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<?> myTodoList
            (@PathVariable Long organizationId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Long page,
             @RequestParam(name = "size", required = false, defaultValue = "10") Long size,
             @RequestParam(name = "flowName") String flowName)
    {
        Page<PersonalTodoDTO.PersonalTodoViewDTO> personalTodoViewDTOS =
                workFlowService.myTodoList(organizationId, page, size, flowName);
        return Results.success(personalTodoViewDTOS);
    }


    @ApiOperation(value = "withdrawl specific")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<?> withDrawlSpecific
            (@PathVariable Long organizationId,
             @RequestParam(name = "flowKey")String flowKey,
             @RequestParam(name = "businessKey") String businessKey)
    {
        workFlowService.withDrawlSpecific(organizationId, flowKey, businessKey);
        return Results.success();
    }

    @ApiOperation(value = "approval history")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping(
            path = "/approval-history"
    )
    public ResponseEntity<?> approvalHistory
            (@PathVariable Long organizationId,
             @RequestParam(name = "flowKey")String flowKey,
             @RequestParam(name = "businessKey") String businessKey)
    {
        List<RunTaskHistory> approvedHistory = workFlowService.getApprovedHistory(organizationId, flowKey, businessKey);
        return Results.success(approvedHistory);
    }

}
