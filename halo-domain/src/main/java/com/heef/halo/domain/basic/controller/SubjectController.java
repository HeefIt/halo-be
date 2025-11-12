package com.heef.halo.domain.basic.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.result.PageResult;
import com.heef.halo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 新增题目
     * @param subjectInfoDTO
     * @return
     */
    @PostMapping("/info/add")
    public Result<Boolean> addSubject(@RequestBody SubjectInfoDTO subjectInfoDTO){
        try{
            if(log.isInfoEnabled()){
                log.info("SubjectController.addSubject.subjectInfoDTO: {}", JSON.toJSONString(subjectInfoDTO));
            }
            Boolean result = subjectService.addSubject(subjectInfoDTO);
            return Result.ok("新增题目结果为:" + result);
        }catch (Exception e){
            log.error("新增题目失败:", e);
            return Result.fail("新增题目失败:" + e.getMessage());
        }
    }


}