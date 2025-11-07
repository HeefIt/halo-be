package com.heef.halo.domain.basic.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.heef.halo.domain.basic.dto.fileDTO.FileDTO;
import com.heef.halo.domain.basic.service.MinioStorageFileService;
import com.heef.halo.result.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}