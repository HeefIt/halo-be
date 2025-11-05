package com.heef.halo.domain.basic.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 敏感词表(SensitiveWords)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SensitiveWords implements Serializable {
    private static final long serialVersionUID = 382795717196612067L;
    /**
     * id
     */
    private Long id;
    /**
     * 内容
     */
    private String words;
    /**
     * 1=黑名单 2=白名单
     */
    private Integer type;
    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;

}
