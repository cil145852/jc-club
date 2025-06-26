package com.cjl.subject.application.controller;

import com.cjl.subject.application.convert.SubjectCategoryDTOConverter;
import com.cjl.subject.application.convert.SubjectLabelDTOConverter;
import com.cjl.subject.application.dto.SubjectCategoryDTO;
import com.cjl.subject.common.entity.Result;
import com.cjl.subject.domain.entity.SubjectCategoryBO;
import com.cjl.subject.domain.service.SubjectCategoryDomainService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:42
 * @Description
 */
@RestController
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {
    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(false);
        }
    }

    /**
     * 查询一级分类
     *
     * @return
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
        List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
        List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBoListToDtoList(subjectCategoryBOList);
        return Result.ok(subjectCategoryDTOList);
    }

    /**
     * 查询二级分类
     *
     * @return
     */
    @PostMapping("/queryCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> queryCategoryByPrimary(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父分类id不能为空");
        SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
        List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
        List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBoListToDtoList(subjectCategoryBOList);
        return Result.ok(subjectCategoryDTOList);
    }

    /**
     * 修改分类
     *
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
        Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
        return Result.ok(result);
    }

    /**
     * 删除分类
     *
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
        Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
        return Result.ok(result);
    }

    /**
     * 查询大分类下的分类和标签信息
     */
    @PostMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
        SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToBO(subjectCategoryDTO);
        List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBO);
        List<SubjectCategoryDTO> subjectCategoryDTOList = subjectCategoryBOList.stream().map(bo -> {
            SubjectCategoryDTO dto = SubjectCategoryDTOConverter.INSTANCE.convertBOToDTO(bo);
            dto.setSubjectLabelDTOList(SubjectLabelDTOConverter.INSTANCE.convertBOListToDTOList(bo.getSubjectLabelBOList()));
            return dto;
        }).collect(Collectors.toList());
        return Result.ok(subjectCategoryDTOList);
    }

}
