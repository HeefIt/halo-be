package com.heef.halo.domain.basic.dto.staticDTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 排行榜详情数据DTO
 */
@Data
public class RankDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前用户的排名
     */
    private Integer currentUserRank;

    /**
     * 当前用户的排行数值
     */
    private Integer currentUserValue;

    /**
     * 排行榜列表数据
     */
    private List<RankDTO> rankings;
}