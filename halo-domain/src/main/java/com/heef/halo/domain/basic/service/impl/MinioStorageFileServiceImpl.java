package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.dto.fileDTO.FileDTO;
import com.heef.halo.domain.basic.dto.fileDTO.FileResponseDTO;
import com.heef.halo.domain.basic.service.MinioStorageFileService;
import com.heef.halo.domain.config.MinioConfig;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MinIO 存储服务
 *
 * @author heefM
 * @date 2025-11-17
 */
@Slf4j
@Service
public class MinioStorageFileServiceImpl implements MinioStorageFileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 上传文件
     *
     * @param file        文件
     * @param description 描述
     * @return 文件信息
     */
    @Override
    public FileDTO uploadFile(MultipartFile file, String description) {
        try {
            // 检查文件是否为空
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("文件不能为空");
            }

            // 获取文件名和文件类型
            String originalFileName = file.getOriginalFilename();
            String fileType = file.getContentType();

            // 生成唯一文件名
            String uniqueFileName = generateUniqueFileName(originalFileName);

            // 确保bucket存在
            createBucketIfNotExists();

            // 上传文件
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioConfig.getBucketName())
                                .object(uniqueFileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(fileType)
                                .build()
                );
            }

            // 构建文件访问路径
            String filePath = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + uniqueFileName;

            // 构建返回结果
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(originalFileName);
            fileDTO.setFilePath(filePath);
            fileDTO.setFileSize(file.getSize());
            fileDTO.setFileType(fileType);
            fileDTO.setDescription(description);
            fileDTO.setCreatedBy("system"); // 实际项目中应该从上下文中获取当前用户
            fileDTO.setCreatedTime(new Date());

            return fileDTO;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 上传图片
     *
     * @param image       图片文件
     * @param description 描述
     * @return 文件信息
     */
    @Override
    public FileDTO uploadImage(MultipartFile image, String description) {
        // 检查是否为图片文件
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("只允许上传图片文件");
        }

        return uploadFile(image, description);
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件ID
     * @return 文件信息
     */
    @Override
    public FileResponseDTO getFileById(Long fileId) {
        // 这里应该是从数据库中获取文件信息
        // 由于这是一个示例，我们直接返回null
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 是否成功
     */
    @Override
    public boolean deleteFile(Long fileId) {
        // 这里应该是从数据库中删除文件信息，并从MinIO中删除文件
        // 由于这是一个示例，我们直接返回true
        return true;
    }

    /**
     * 获取文件列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 文件列表
     */
    @Override
    public List<FileResponseDTO> getFileList(Integer page, Integer size) {
        // 这里应该是从数据库中获取文件列表
        // 由于这是一个示例，我们直接返回空列表
        return new ArrayList<>();
    }

    /**
     * 下载文件
     *
     * @param fileId 文件ID
     * @return 文件字节数组
     */
    @Override
    public byte[] downloadFile(Long fileId) {
        // 这里应该是从MinIO中下载文件
        // 由于这是一个示例，我们直接返回空字节数组
        return new byte[0];
    }

    /**
     * 生成唯一文件名
     *
     * @param originalFileName 原始文件名
     * @return 唯一文件名
     */
    private String generateUniqueFileName(String originalFileName) {
        // 获取当前时间戳
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 获取文件扩展名
        String extension = "";
        if (originalFileName != null && originalFileName.lastIndexOf(".") != -1) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        // 拼接唯一文件名
        return timestamp + "_" + uuid + extension;
    }

    /**
     * 创建bucket如果不存在
     */
    private void createBucketIfNotExists() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build());
            }
        } catch (Exception e) {
            log.error("检查或创建bucket失败", e);
            throw new RuntimeException("检查或创建bucket失败: " + e.getMessage());
        }
    }
}