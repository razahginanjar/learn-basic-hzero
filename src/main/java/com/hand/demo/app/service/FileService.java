package com.hand.demo.app.service;

import com.hand.demo.api.dto.FileConfigUploadDTO;
import com.hand.demo.api.dto.response.FileResponse;
import feign.Response;
import org.hzero.boot.file.dto.FileDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    String uploadFile(String fileName,
                      String Dir,
                      String newFileName,
                      Long tenantId);
    String DownloadFile(String fileKey,
                        Long tenantId,
                        String fileName);
    void deleteFile(Long tenantId, List<String> attachIds);
    List<FileDTO> getFile(Long tenantId, String attachId);

    String DownloadImage(String fileKey,
                         Long tenantId,
                         String fileName);
    String UploadImage(String fileName,
                       String Dir,
                       String newFileName,
                       Long tenantId);
    Response addWaterMark(
            Long organizationId,
            String dir,
            String waterMarkCode
    );

    List<FileConfigUploadDTO> getInfoUploadConfig(Long tenantId);
}
