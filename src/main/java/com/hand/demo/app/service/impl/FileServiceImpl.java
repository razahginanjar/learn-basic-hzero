package com.hand.demo.app.service.impl;

import com.hand.demo.app.service.FileService;
import com.hand.demo.infra.constant.ConstantPath;
import feign.Response;
import org.hzero.boot.file.FileClient;
import org.hzero.boot.file.constant.FileType;
import org.hzero.boot.file.dto.FileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final FileClient fileClient;
    private static final String bucketName = "gp1";
    private static final String storageCode = "MINIO_21995";

    public FileServiceImpl(FileClient fileClient) {
        this.fileClient = fileClient;
    }

    @Override
    public String uploadFile(String fileName,
                             String Dir,
                             String newFileName,
                             Long tenantId){
        String attachmentUUID = fileClient.getAttachmentUUID(0L);

        try{
            File file = new File(ConstantPath.TXT_DIR+ConstantPath.UP_DIR+ fileName);

            byte[] bytes = new byte[(int) file.length()];
            if(!file.exists())
            {
                  throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            try (FileInputStream inputStream = new FileInputStream(file)) {
                inputStream.read(bytes);
            }
            log.info(bytes.toString());
            String s1 = fileClient.uploadAttachment(
                    tenantId,
                    bucketName,
                    Dir,
                    attachmentUUID,
                    newFileName,
                    FileType.Text.TXT,
                    storageCode,
                    bytes
            );
            return s1;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public String DownloadFile(String fileKey,
                               Long tenantId,
                               String fileName) {
        try {
            // Download the file
            InputStream gp2 = fileClient.downloadFile(
                    tenantId,
                    fileKey);

            // Read the input stream into a byte array manually (for Java 8 compatibility)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gp2.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();

            // Create the file to save the downloaded content
            File file = new File(ConstantPath.TXT_DIR+ConstantPath.DO_DIR+fileName);
            file.getParentFile().mkdirs(); // Ensure directories exist

            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(bytes);
                log.info("File downloaded successfully to {}", file.getAbsolutePath());
            }

            return "File saved at: " + file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }

    @Override
    public void deleteFile(Long tenantId, List<String> attachIds) {
        List<String> urls = new ArrayList<>();
        for (String attachId : attachIds) {
            List<FileDTO> files = getFile(tenantId, attachId);
            for (FileDTO file : files) {
                String fileUrl = file.getFileUrl();
                urls.add(fileUrl);
            }
        }
        fileClient.deleteFileByUrl(tenantId, bucketName, urls);
    }

    @Override
    public List<FileDTO> getFile(Long tenantId, String attachId) {
        return fileClient.getAttachmentFiles(tenantId, bucketName,
                attachId);
    }

    @Override
    public String DownloadImage(String fileKey,
                                Long tenantId,
                                String fileName) {
        try {
            // Download the file
            InputStream gp2 = fileClient.downloadFile(
                    tenantId,
                    fileKey);

            // Read the input stream into a byte array manually (for Java 8 compatibility)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gp2.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();

            // Create the file to save the downloaded content
            File file = new File(ConstantPath.TXT_DIR+ConstantPath.DO_DIR+fileName);
            file.getParentFile().mkdirs(); // Ensure directories exist

            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(bytes);
                log.info("File downloaded successfully to {}", file.getAbsolutePath());
            }

            return "File saved at: " + file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String UploadImage(String fileName,
                              String Dir,
                              String newFileName,
                              Long tenantId) {
        String attachmentUUID = fileClient.getAttachmentUUID(tenantId);

        try{
            File file = new File(ConstantPath.IMG_DIR+ConstantPath.UP_DIR+ fileName);

            byte[] bytes = new byte[(int) file.length()];
            if(!file.exists())
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            try (FileInputStream inputStream = new FileInputStream(file)) {
                inputStream.read(bytes);
            }
            log.info(bytes.toString());
            String s1 = fileClient.uploadAttachment(
                    tenantId,
                    bucketName,
                    Dir,
                    attachmentUUID,
                    newFileName,
                    FileType.Image.JPG,
                    storageCode,
                    bytes
            );
            return s1;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public Response addWaterMark(Long organizationId, String fileKey, String waterMarkCode) {
        return fileClient.watermarkByKey(organizationId, fileKey, waterMarkCode);
    }

}
