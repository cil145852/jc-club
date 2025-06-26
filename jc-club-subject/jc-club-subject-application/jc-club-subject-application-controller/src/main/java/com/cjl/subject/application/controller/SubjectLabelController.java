package com.cjl.subject.application.controller;

import com.cjl.subject.application.convert.SubjectLabelDTOConverter;
import com.cjl.subject.application.dto.SubjectLabelDTO;
import com.cjl.subject.common.entity.Result;
import com.cjl.subject.domain.entity.SubjectLabelBO;
import com.cjl.subject.domain.service.SubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-03-16:54
 * @Description 标签controller
 */
@RestController
@RequestMapping("/subject/label")
@Slf4j
public class SubjectLabelController {
    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;

    /**
     * 添加标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOToBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(false);
        }
    }

    /**
     * 修改标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOToBO(subjectLabelDTO);
        Boolean result = subjectLabelDomainService.update(subjectLabelBO);
        return Result.ok(result);
    }

    /**
     * 删除标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOToBO(subjectLabelDTO);
        Boolean result = subjectLabelDomainService.delete(subjectLabelBO);
        return Result.ok(result);
    }

    /**
     * 查询分类下的标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/queryLabelByCategoryId")
    public Result<List<SubjectLabelDTO>> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOToBO(subjectLabelDTO);
        List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
        List<SubjectLabelDTO> result = SubjectLabelDTOConverter.INSTANCE.convertBOListToDTOList(subjectLabelBOList);
        return Result.ok(result);
    }
}
