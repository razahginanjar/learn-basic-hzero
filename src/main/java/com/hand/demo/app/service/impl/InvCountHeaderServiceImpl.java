package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.InvCountRequestDTO;
import com.hand.demo.infra.constant.StatusDoc;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hand.demo.app.service.InvCountHeaderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.hand.demo.domain.entity.InvCountHeader;
import com.hand.demo.domain.repository.InvCountHeaderRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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
    public InvCountHeader insertOrUpdate(InvCountRequestDTO invCountRequestDTO, Long tenantId) {
        InvCountHeader invCountHeader =
                invCountHeaderRepository.
                        selectByCountNumber(invCountRequestDTO.getBusinessKey());
        if (Objects.isNull(invCountHeader)) {
            throw new CommonException("demo-47837.inv_not_found_error", invCountRequestDTO.getBusinessKey());
        }
        if(invCountRequestDTO.getStatusDoc().equals(StatusDoc.APPROVAL)){
            invCountHeader.setApprovedTime(Date.from(Instant.now()));
        }
        invCountHeader.setCountStatus(invCountRequestDTO.getStatusDoc());
        invCountHeader.setWorkflowId(invCountRequestDTO.getWorkFlowId());
        invCountHeader.setTenantId(tenantId);
        update(invCountHeader);
        return invCountHeaderRepository.selectByCountNumber(invCountRequestDTO.getBusinessKey());
    }

    @Override
    public InvCountHeader getByCountNumber(String countNumber) {
        return invCountHeaderRepository.selectByCountNumber(countNumber);
    }


    public void create(InvCountRequestDTO invCountRequestDTO, Long tenantId) {
        log.info("It is in create");
        InvCountHeader invCountHeader = new InvCountHeader();
        invCountHeader.setCountNumber(invCountRequestDTO.getBusinessKey());
        invCountHeader.setCountStatus(invCountRequestDTO.getStatusDoc());
        invCountHeader.setWorkflowId(invCountRequestDTO.getWorkFlowId());
        invCountHeader.setTenantId(tenantId);
        invCountHeaderRepository.insertSelective(invCountHeader);
    }

    public void update(InvCountHeader updateInvCountHeader) {
        invCountHeaderRepository.updateByPrimaryKey(updateInvCountHeader);
    }
}

