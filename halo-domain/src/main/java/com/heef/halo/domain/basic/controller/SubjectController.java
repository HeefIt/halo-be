package com.heef.halo.domain.basic.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.heef.halo.domain.basic.dto.staticDTO.RankDTO;
import com.heef.halo.domain.basic.dto.staticDTO.RankDetailDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.*;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.result.PageResult;
import com.heef.halo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * subject-题目控制层
 *
 * @author heefM
 * @date 2025-11-05
 */
@Slf4j
@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    /**
     * 新增题目分类
     *
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("category/add")
    public Result<Boolean> addCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.addCategory:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            Boolean result = subjectService.addCategory(subjectCategoryDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("新增题目分类失败: ", e);
            return Result.fail("新增题目分类失败: " + e.getMessage());
        }

    }

    /**
     * 分页查询分类列表数据
     *
     * @param subjectCategoryDTO
     * @param pageNum
     * @param pageSize
     * @return
     */

    @SaCheckLogin
    @GetMapping("/category/selectPage")
    public Result<PageResult<SubjectCategoryDTO>> selectPage(SubjectCategoryDTO subjectCategoryDTO,
                                                             @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectPage.dto: {}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            PageResult<SubjectCategoryDTO> pageResult = subjectService.selectPage(subjectCategoryDTO, pageNum, pageSize);
            if (log.isInfoEnabled()) {
                log.info("分类查询结果: {}"
                        , JSON.toJSONString(pageResult));
            }
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("分页查询分类列表数据接口: ", e);
            return Result.fail("分页查询分类列表数据失败: " + e.getMessage());
        }
    }


    /**
     * 修改分类
     *
     * @param id
     * @param subjectCategoryDTO
     * @return
     */
    @PutMapping("/category/update/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.update.dto: {}", JSON.toJSONString(subjectCategoryDTO));
            }
            Boolean result = subjectService.update(id, subjectCategoryDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("修改分类失败:", e);
            return Result.fail("修改分类失败:" + e.getMessage());
        }
    }


    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/category/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.delete.dto: {}", id);
            }
            Boolean result = subjectService.delete(id);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("删除分类失败:", e);
            return Result.fail("删除分类失败:" + e.getMessage());
        }
    }


    /**
     *      * 查询分类下大类就是把分类全部查出来父分类--子分类) 根据父分类id查询子分类
     *      *
     *      * @param subjectCategoryDTO
     *      * @return
     */
    @PostMapping("/category/selectCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> selectPage(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectCategoryByPrimary.dto: {}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            List<SubjectCategoryDTO> subjectCategoryDTOList = subjectService.selectCategoryByPrimary(subjectCategoryDTO);
            if (log.isInfoEnabled()) {
                log.info("查询分类下大类: {}"
                        , JSON.toJSONString(subjectCategoryDTOList));
            }
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("查询分类下大类接口: ", e);
            return Result.fail("查询分类下大类: " + e.getMessage());
        }
    }


    /**
     * 根据分类id--查询子分类及其标签(to user)
     *
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("/category/selectCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> selectCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectCategoryAndLabel.dto: {}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            List<SubjectCategoryDTO> subjectCategoryDTOList = subjectService.selectCategoryAndLabel(subjectCategoryDTO);
            if (log.isInfoEnabled()) {
                log.info("controller: 根据分类id--查询子分类及其标签: {}"
                        , JSON.toJSONString(subjectCategoryDTOList));
            }
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("根据分类id--查询子分类及其标签接口: ", e);
            return Result.fail("根据分类id--查询子分类及其标签失败: " + e.getMessage());
        }
    }






    /*---------------------------------------------------标签模块-------------------------------------------------------*/
    /*---------------------------------------------------标签模块-------------------------------------------------------*/
    /*---------------------------------------------------标签模块-------------------------------------------------------*/


    /**
     * 新增题目标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("label/add")
    public Result<Boolean> addLabel(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.addLabel:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Boolean result = subjectService.addLabel(subjectLabelDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("新增题目标签失败: ", e);
            return Result.fail("新增题目标签失败: " + e.getMessage());
        }

    }

    /**
     * 分页查询标签列表数据
     *
     * @param subjectLabelDTO
     * @param pageNum
     * @param pageSize
     * @return
     */

    @SaCheckLogin
    @GetMapping("/label/selectPage")
    public Result<PageResult<SubjectLabelDTO>> selectPageLabel(SubjectLabelDTO subjectLabelDTO,
                                                               @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectPageLabel.dto: {}"
                        , JSON.toJSONString(subjectLabelDTO));
            }
            PageResult<SubjectLabelDTO> pageResult = subjectService.selectPageLabel(subjectLabelDTO, pageNum, pageSize);
            if (log.isInfoEnabled()) {
                log.info("标签查询结果: {}"
                        , JSON.toJSONString(pageResult));
            }
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("分页查询标签列表数据接口: ", e);
            return Result.fail("分页查询标签列表数据失败: " + e.getMessage());
        }
    }


    /**
     * 修改标签
     *
     * @param id
     * @param subjectLabelDTO
     * @return
     */
    @SaCheckLogin
    @PutMapping("/label/update/{id}")
    public Result<Boolean> updateLabel(@PathVariable Long id, @RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.updateLabel.dto: {}", JSON.toJSONString(subjectLabelDTO));
            }
            Boolean result = subjectService.updateLabel(id, subjectLabelDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("修改标签失败:", e);
            return Result.fail("修改标签失败:" + e.getMessage());
        }
    }


    /**
     * 删除标签
     *
     * @param id
     * @param
     * @return
     */
    @SaCheckLogin
    @DeleteMapping("/label/delete/{id}")
    public Result<Boolean> deleteLabel(@PathVariable Long id) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.deleteLabel: {}", id);
            }
            Boolean result = subjectService.deleteLabel(id);
            return Result.ok("删除标签结果为:" + result);
        } catch (Exception e) {
            log.error("删除标签失败:", e);
            return Result.fail("删除标签失败:" + e.getMessage());
        }
    }




    /*---------------------------------------------------题目模块-----------------------------------------------------------*/
    /*---------------------------------------------------题目模块-----------------------------------------------------------*/
    /*---------------------------------------------------题目模块-----------------------------------------------------------*/

    /**
     * 新增题目
     *
     * @param subjectInfoDTO
     * @return
     */
    @PostMapping("/info/add")
    public Result<Boolean> addSubject(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.addSubject.subjectInfoDTO: {}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkNotNull(subjectInfoDTO.getLabelIds(), "标签不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getCategoryIds(), "分类不能为空");
            Boolean result = subjectService.addSubject(subjectInfoDTO);
            return Result.ok("新增题目结果为:" + result);
        } catch (Exception e) {
            log.error("新增题目失败:", e);
            return Result.fail("新增题目失败:" + e.getMessage());
        }
    }


    /**
     * 分页查询题目列表(管理后台)
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/info/selectPageToAdmin")
    public Result<PageResult<SubjectInfoDTO>> selectSubjectPage(SubjectInfoDTO subjectInfoDTO,
                                                                @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectSubjectPage.subjectInfoDTO: {}", JSON.toJSONString(subjectInfoDTO));
            }
            PageResult<SubjectInfoDTO> pageResult = subjectService.selectSubjectPage(subjectInfoDTO, pageNum, pageSize);
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("分页查询题目列表失败: {}", e.getMessage(), e);
            return Result.fail("分页查询题目列表失败:" + e.getMessage());
        }
    }

    /**
     * 分页查询题目列表(面向用户)
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/info/selectPageToUser")
    public Result<PageResult<SubjectInfoDTO>> selectSubjectPage2(SubjectInfoDTO subjectInfoDTO,
                                                                 @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.selectSubjectPage2.subjectInfoDTO: {}", JSON.toJSONString(subjectInfoDTO));
            }
            PageResult<SubjectInfoDTO> pageResult = subjectService.selectSubjectPage2(subjectInfoDTO, pageNum, pageSize);
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("分页查询题目列表失败: {}", e.getMessage(), e);
            return Result.fail("分页查询题目列表失败:" + e.getMessage());
        }
    }

    /**
     * 条件查看题目详情
     *
     * @param
     * @return
     */
    @GetMapping("/info/selectSubjectInfo")
    public Result<SubjectInfoDTO> selectSubjectInfo(SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo: {}", JSON.toJSONString(subjectInfoDTO));
            }
            SubjectInfoDTO subjectInfoDTOResult = subjectService.selectSubjectInfo(subjectInfoDTO);
            return Result.ok(subjectInfoDTOResult);
        } catch (Exception e) {
            log.error("查看题目详情失败: {}", e.getMessage(), e);
            return Result.fail("查看题目详情失败:" + e.getMessage());
        }
    }

    /**
     * 编辑题目
     *
     * @param
     * @return
     */
    @PostMapping("/info/updateSubjectInfo")
    public Result<Boolean> updateSubject(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.updateSubjectInfo: {}", JSON.toJSONString(subjectInfoDTO));
            }
            Boolean result = subjectService.updateSubjectInfo(subjectInfoDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("编辑题目失败: {}", e.getMessage(), e);
            return Result.fail("编辑题目失败:" + e.getMessage());
        }
    }


    /**
     * 删除题目
     *
     * @param
     * @return
     */
    @PostMapping("/info/deleteSubjectInfo")
    public Result<Boolean> deleteSubject(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.deleteSubjectInfo: {}", JSON.toJSONString(subjectInfoDTO));
            }
            Boolean result = subjectService.deleteSubject(subjectInfoDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("删除题目失败: {}", e.getMessage(), e);
            return Result.fail("删除题目失败:" + e.getMessage());
        }
    }

    /*---------------------------------------------------刷题模块-----------------------------------------------------------*/
    /*---------------------------------------------------刷题模块-----------------------------------------------------------*/
    /*---------------------------------------------------刷题模块-----------------------------------------------------------*/

    /**
     * 保存刷题记录
     *
     * @param
     * @return
     */
    @PostMapping("/record/SaveRecord")
    public Result<Boolean> SaveRecord(@RequestBody SubjectRecordDTO subjectRecordDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.addRecord.dto: {}", JSON.toJSONString(subjectRecordDTO));
            }
            Boolean result = subjectService.SaveRecord(subjectRecordDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("保存刷题记录失败: {}", e.getMessage(), e);
            return Result.fail("保存刷题记录失败:" + e.getMessage());
        }
    }
    
    /**
     * 获取当前用户答题记录
     * (后续可以做排行榜(排行榜类型---总排行榜和今日排行榜)
     *
     * @param userId
     * @return
     */
    @GetMapping("/record/getRecordByUser")
    public Result<List<SubjectRecordDTO>> getRecordByUser(@RequestParam Long userId) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getRecordByUser.userId: {}", userId);
            }
            List<SubjectRecordDTO> recordList = subjectService.getRecordByUser(userId);
            return Result.ok(recordList);
        } catch (Exception e) {
            log.error("获取用户答题记录失败: {}", e.getMessage(), e);
            return Result.fail("获取用户答题记录失败:" + e.getMessage());
        }
    }

    /**
     * 获取题目答题记录
     * (后续可以做排行榜(排行榜类型---总排行榜和今日排行榜)
     * @param subjectId
     * @return
     */
    @GetMapping("/record/getRecordBySubject")
    public Result<List<SubjectRecordDTO>> getRecordBySubject(@RequestParam Long subjectId) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getRecordBySubject.subjectId: {}", subjectId);
            }
            List<SubjectRecordDTO> recordList = subjectService.getRecordBySubject(subjectId);
            return Result.ok(recordList);
        } catch (Exception e) {
            log.error("获取题目答题记录失败: {}", e.getMessage(), e);
            return Result.fail("获取题目答题记录失败:" + e.getMessage());
        }
    }
    
    /**
     * 获取当前用户已解决的题目数量
     * 
     * @param userId 用户ID
     * @return 已解决的题目数量
     */
    @GetMapping("/record/getSolvedProblemsCount")
    public Result<Integer> getSolvedProblemsCount(@RequestParam Long userId) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSolvedProblemsCount.userId: {}", userId);
            }
            int count = subjectService.getSolvedProblemsCount(userId);
            return Result.ok(count);
        } catch (Exception e) {
            log.error("获取用户已解决题目数量失败: {}", e.getMessage(), e);
            return Result.fail("获取用户已解决题目数量失败:" + e.getMessage());
        }
    }
    
    /**
     * 获取当前用户尝试的题目数量
     * 
     * @param userId 用户ID
     * @return 尝试的题目数量
     */
    @GetMapping("/record/getAttemptedProblemsCount")
    public Result<Integer> getAttemptedProblemsCount(@RequestParam Long userId) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getAttemptedProblemsCount.userId: {}", userId);
            }
            int count = subjectService.getAttemptedProblemsCount(userId);
            return Result.ok(count);
        } catch (Exception e) {
            log.error("获取用户尝试题目数量失败: {}", e.getMessage(), e);
            return Result.fail("获取用户尝试题目数量失败:" + e.getMessage());
        }
    }

    /**
     * 获取排行榜数据(获取排行榜列表数据)
     * 显示排行榜的基本列表信息
     * timeRange: 时间范围，可选值为 today（今日）、week（本周）、month（本月）
     * rankingType: 排行类型，可选值为 problemCount（刷题数）、score（得分）、correctCount（正确数）
     * @param timeRange
     * @param rankType
     * @return
     */
    @GetMapping("/record/getRankList")
    public Result<List<RankDTO>> getRankList(
            @RequestParam String timeRange, 
            @RequestParam String rankType) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getRankList: {},{}", timeRange,rankType);
            }
            List<RankDTO> rankList = subjectService.getRankList(timeRange, rankType);
            return Result.ok(rankList);
        } catch (Exception e) {
            log.error("获取排行榜数据失败: {}", e.getMessage(), e);
            return Result.fail("获取排行榜数据失败:" + e.getMessage());
        }
    }


    /**
     * 获取排行榜详情
     * 显示排行榜的详细信息，包括当前用户在排行榜中的具体位置
     *
     * @param timeRange 选择获取排行榜的时间范围
     * @param rankType 排行榜类型
     * @param userId 用户ID
     * @return 排行榜详情
     */
    @GetMapping("/record/getRankDetail")
    public Result<RankDetailDTO> getRankDetail(
            @RequestParam String timeRange, 
            @RequestParam String rankType,
            @RequestParam Long userId) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getRankDetail: timeRange={}, rankType={}, userId={}", timeRange, rankType, userId);
            }
            RankDetailDTO rankDetail = subjectService.getRankDetail(timeRange, rankType, userId);
            return Result.ok(rankDetail);
        } catch (Exception e) {
            log.error("获取排行榜详情数据失败: {}", e.getMessage(), e);
            return Result.fail("获取排行榜详情数据失败:" + e.getMessage());
        }
    }
}