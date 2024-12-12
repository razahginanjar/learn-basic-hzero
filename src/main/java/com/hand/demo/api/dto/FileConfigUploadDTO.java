package com.hand.demo.api.dto;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@ModifyAudit
//@Table(name = "file")
@VersionAudit
public class FileConfigUploadDTO extends AuditDomain {
    private String bucketName;
    private String contentType;
    private String directory;
    private String storageSize;
    private String storageUnit;
}
