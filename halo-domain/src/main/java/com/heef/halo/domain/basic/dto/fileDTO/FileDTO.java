package com.heef.halo.domain.basic.dto.fileDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息DTO
 *
 * @author heefM
 * @date 2025-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 文件ID
     */
    private Long id;
    
    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件大小
     */
    private Long fileSize;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 文件描述
     */
    private String description;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private Date createdTime;
}