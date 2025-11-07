package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.dto.fileDTO.FileDTO;
import com.heef.halo.domain.basic.dto.fileDTO.FileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MinioStorageFileService {
    /**
     * 上传文件
     * 
     * @param file 文件
     * @param description 描述
     * @return 文件信息
     */
    FileDTO uploadFile(MultipartFile file, String description);

    /**
     * 上传图片
     * 
     * @param image 图片文件
     * @param description 描述
     * @return 文件信息
     */
    FileDTO uploadImage(MultipartFile image, String description);

    /**
     * 获取文件信息
     * 
     * @param fileId 文件ID
     * @return 文件信息
     */
    FileResponseDTO getFileById(Long fileId);

    /**
     * 删除文件
     * 
     * @param fileId 文件ID
     * @return 是否成功
     */
    boolean deleteFile(Long fileId);

    /**
     * 获取文件列表
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 文件列表
     */
    List<FileResponseDTO> getFileList(Integer page, Integer size);

    /**
     * 下载文件
     * 
     * @param fileId 文件ID
     * @return 文件字节数组
     */
    byte[] downloadFile(Long fileId);
}