package com.cjl.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.application.convert.SubjectInfoDTOConverter;
import com.cjl.subject.application.convert.SubjectOptionDTOConverter;
import com.cjl.subject.application.dto.SubjectInfoDTO;
import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.entity.Result;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.domain.service.SubjectInfoDomainService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-16:36
 * @Description
 */
@RestController
@RequestMapping("/subject")
@Slf4j
public class SubjectController {
    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;

    /**
     * 添加题目
     *
     * @param subjectInfoDTO
     * @return
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.SubjectInfoDTO:{}", JSON.toJSONString(subjectInfoDTO));
            }
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToBo(subjectInfoDTO);
            subjectInfoBO.setOptionList(SubjectOptionDTOConverter.INSTANCE.convertListDtoToBo(subjectInfoDTO.getOptionList()));
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(false);
        }
    }

    @PostMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToBo(subjectInfoDTO);
        PageResult<SubjectInfoBO> boPageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);
        PageResult<SubjectInfoDTO> dtoPageResult = new PageResult<>();
        dtoPageResult.setRecords(SubjectInfoDTOConverter.INSTANCE.convertListBoToDto(boPageResult.getResult()));
        return Result.ok(dtoPageResult);
    }

    @PostMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToBo(subjectInfoDTO);
        subjectInfoBO = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
        SubjectInfoDTO dto = SubjectInfoDTOConverter.INSTANCE.convertBoToDto(subjectInfoBO);
        return Result.ok(dto);
    }

    /**
     * es全文检索
     */
    @PostMapping("/getSubjectPageBySearch")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPageBySearch(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPageBySearch.SubjectInfoDTO:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectInfoDTO.getKeyWord()), "搜索关键字不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToBo(subjectInfoDTO);
            subjectInfoBO.setPageNo(subjectInfoDTO.getPageNo());
            subjectInfoBO.setPageSize(subjectInfoDTO.getPageSize());
            PageResult<SubjectInfoBO> boPageResult = subjectInfoDomainService.getSubjectPageBySearch(subjectInfoBO);
            PageResult<SubjectInfoDTO> pageResult = new PageResult<>();
            pageResult.setRecords(SubjectInfoDTOConverter.INSTANCE.convertListBoToDto(boPageResult.getResult()));
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("SubjectController.getSubjectPageBySearch.error:{}", e.getMessage(), e);
            return Result.fail(null);
        }
    }
}
