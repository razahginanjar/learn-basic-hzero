package com.hand.demo.api.controller.v1;

import com.hand.demo.api.dto.FileConfigUploadDTO;
import com.hand.demo.api.dto.response.FileResponse;
import com.hand.demo.app.service.FileService;
import com.hand.demo.config.SwaggerTags;
import feign.Response;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.file.dto.FileDTO;
import org.hzero.core.base.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = SwaggerTags.FILE)
@RestController("FileController.v1.47837")
@RequestMapping("/v1/ra/{organizationId}/file")
public class FileController extends BaseController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Download File")
    @GetMapping(
            path = "/download"
    )
    public ResponseEntity<?> downloadFile(
            @PathVariable(name = "organizationId") Long organizationId,
            @RequestParam(name = "fileKey") String fileKey,
            @RequestParam(name = "fileName") String fileName)
    {
        String s = fileService.DownloadFile(fileKey, organizationId, fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                s
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Upload File text")
    @PostMapping(
            path = "/upload"
    )
    public ResponseEntity<?> uploadFileTxt(
            @PathVariable(name = "organizationId") Long organizationId,
            @RequestParam(name = "dir") String dir,
            @RequestParam(name = "newFileName") String newFileName,
            @RequestParam(name = "fileName") String fileName)
    {
        String s = fileService.uploadFile(fileName, dir, newFileName, organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(
                s
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Delete File")
    @DeleteMapping(
            path = "/delete"
    )
    public ResponseEntity<?> deleteFile(
            @PathVariable(name = "organizationId") Long tenantId,
            @RequestParam(name = "uuids") List<String> uuids)
    {
        fileService.deleteFile(tenantId, uuids);
        return ResponseEntity.status(HttpStatus.OK).body(
                "deleted success"
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "get info file")
    @GetMapping
    public ResponseEntity<?> getInfoFile(
            @PathVariable(name = "organizationId") Long tenantId,
            @RequestParam String attachId)
    {
        List<FileDTO> file = fileService.getFile(tenantId, attachId);
        return ResponseEntity.status(HttpStatus.OK).body(
                file
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Upload File image")
    @PostMapping(
            path = "/upload-image"
    )
    public ResponseEntity<?> uploadFileImage(
            @PathVariable(name = "organizationId") Long organizationId,
            @RequestParam(name = "dir") String dir,
            @RequestParam(name = "newFileName") String newFileName,
            @RequestParam(name = "fileName") String fileName)
    {
        String s = fileService.UploadImage(fileName, dir, newFileName, organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(
                s
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Download File")
    @GetMapping(
            path = "/download-image"
    )
    public ResponseEntity<?> downloadFileImage(
            @PathVariable(name = "organizationId") Long organizationId,
            @RequestParam(name = "fileKey") String fileKey,
            @RequestParam(name = "fileName") String fileName)
    {
        String s = fileService.DownloadImage(fileKey, organizationId, fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                s
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "watermark")
    @PostMapping(
            path = "/watermark"
    )
    public ResponseEntity<?> addWaterMark(
            @PathVariable(name = "organizationId") Long organizationId,
            @RequestParam(name = "fileKey") String fileKey,
            @RequestParam(name = "waterMarkCode") String waterMarkCode)
    {
        Response response = fileService.addWaterMark(organizationId, fileKey, waterMarkCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "get info config")
    @GetMapping(
            path = "/info-config"
    )
    public ResponseEntity<?> getInfoConfigUpload(
            @PathVariable(name = "organizationId") Long organizationId)
    {
        List<FileConfigUploadDTO> infoUploadConfig = fileService.getInfoUploadConfig(organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(infoUploadConfig
        );
    }

}
