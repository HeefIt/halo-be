package com.heef.halo.domain.basic.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.heef.halo.domain.basic.dto.fileDTO.FileDTO;
import com.heef.halo.domain.basic.dto.fileDTO.FileResponseDTO;
import com.heef.halo.domain.basic.service.MinioStorageFileService;
import com.heef.halo.result.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传控制层
 *
 * @author heefM
 * @date 2025-11-07
 */
@Slf4j
@RestController
@RequestMapping("api/file")
public class FileController {

    @Resource
    private MinioStorageFileService fileService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 上传结果
     */
    @SaCheckLogin
    @PostMapping("/upload")
    public Result<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                      @RequestParam(required = false) String description) {
        if (log.isInfoEnabled()) {
            log.info("文件上传: {}, 描述: {}", file.getOriginalFilename(), description);
        }

        FileDTO fileDTO = fileService.uploadFile(file, description);
        if (fileDTO == null) {
            return Result.fail("文件上传失败");
        }
        return Result.ok(fileDTO).setMessage("文件上传成功!");
    }

    /**
     * 图片上传
     *
     * @param image 图片文件
     * @return 上传结果
     */
    @SaCheckLogin
    @PostMapping("/upload/image")
    public Result<FileDTO> uploadImage(@RequestParam("image") MultipartFile image,
                                       @RequestParam(required = false) String description) {
        if (log.isInfoEnabled()) {
            log.info("图片上传: {}, 描述: {}", image.getOriginalFilename(), description);
        }

        FileDTO fileDTO = fileService.uploadImage(image, description);
        if (fileDTO == null) {
            return Result.fail("图片上传失败");
        }
        return Result.ok(fileDTO).setMessage("图片上传成功!");
    }
    
    /**
     * 根据ID获取文件信息
     *
     * @param fileId 文件ID
     * @return 文件信息
     */
    @SaCheckLogin
    @GetMapping("/{fileId}")
    public Result<FileResponseDTO> getFileById(@PathVariable Long fileId) {
        if (log.isInfoEnabled()) {
            log.info("获取文件信息: fileId={}", fileId);
        }
        
        FileResponseDTO fileResponseDTO = fileService.getFileById(fileId);
        if (fileResponseDTO == null) {
            return Result.fail("文件不存在");
        }
        return Result.ok(fileResponseDTO).setMessage("获取文件信息成功!");
    }
    
    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping("/{fileId}")
    public Result<Boolean> deleteFile(@PathVariable Long fileId) {
        if (log.isInfoEnabled()) {
            log.info("删除文件: fileId={}", fileId);
        }
        
        boolean result = fileService.deleteFile(fileId);
        if (!result) {
            return Result.fail(false).setMessage("文件删除失败");
        }
        return Result.ok(true).setMessage("文件删除成功!");
    }
    
    /**
     * 获取文件列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 文件列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public Result<List<FileResponseDTO>> getFileList(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        if (log.isInfoEnabled()) {
            log.info("获取文件列表: page={}, size={}", page, size);
        }
        
        List<FileResponseDTO> fileResponseDTOList = fileService.getFileList(page, size);
        return Result.ok(fileResponseDTOList).setMessage("获取文件列表成功!");
    }
}