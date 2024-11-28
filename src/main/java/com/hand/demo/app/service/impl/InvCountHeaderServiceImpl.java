package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.InvCountRequest;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hand.demo.app.service.InvCountHeaderService;
import org.springframework.stereotype.Service;
import com.hand.demo.domain.entity.InvCountHeader;
import com.hand.demo.domain.repository.InvCountHeaderRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Inventory Count Header Table(InvCountHeader)应用服务
 *
 * @author razah
 * @since 2024-11-28 10:08:42
 */
@Service
public class InvCountHeaderServiceImpl implements InvCountHeaderService {
    private static final Logger log = LoggerFactory.getLogger(InvCountHeaderServiceImpl.class);
    @Autowired
    private InvCountHeaderRepository invCountHeaderRepository;

    @Override
    public Page<InvCountHeader> selectList(PageRequest pageRequest, InvCountHeader invCountHeader) {
        return PageHelper.doPageAndSort(pageRequest, () -> invCountHeaderRepository.selectList(invCountHeader));
    }

    @Override
    public void saveData(List<InvCountHeader> invCountHeaders) {
        List<InvCountHeader> insertList = invCountHeaders.stream().filter(line -> line.getCountHeaderId() == null).collect(Collectors.toList());
        List<InvCountHeader> updateList = invCountHeaders.stream().filter(line -> line.getCountHeaderId() != null).collect(Collectors.toList());
        invCountHeaderRepository.batchInsertSelective(insertList);
        invCountHeaderRepository.batchUpdateByPrimaryKeySelective(updateList);
    }

    @Override
    public InvCountHeader insertOrUpdate(InvCountRequest invCountRequest, Long tenantId) {
        InvCountHeader invCountHeader = invCountHeaderRepository.selectByCountNumber(invCountRequest.getBusinessKey());
        if (Objects.isNull(invCountHeader)) {
            create(invCountRequest, tenantId);
            return invCountHeaderRepository.selectByCountNumber(invCountRequest.getBusinessKey());
        }
        else {
            log.info("It is in update");
            invCountHeader.setApprovedTime(Date.from(Instant.now()));
            invCountHeader.setCountStatus(invCountRequest.getStatusDoc());
            invCountHeader.setWorkflowId(invCountRequest.getWorkFlowId());
            invCountHeader.setTenantId(tenantId);
            update(invCountHeader);
        }
        return invCountHeaderRepository.selectByCountNumber(invCountRequest.getBusinessKey());
    }


    public void create(InvCountRequest invCountRequest, Long tenantId) {
        log.info("It is in create");
        InvCountHeader invCountHeader = new InvCountHeader();
        invCountHeader.setCountNumber(invCountRequest.getBusinessKey());
        invCountHeader.setCountStatus(invCountRequest.getStatusDoc());
        invCountHeader.setWorkflowId(invCountRequest.getWorkFlowId());
        invCountHeader.setTenantId(tenantId);
        invCountHeaderRepository.insertSelective(invCountHeader);
    }

    public void update(InvCountHeader updateInvCountHeader) {
        invCountHeaderRepository.updateByPrimaryKey(updateInvCountHeader);
    }
}

