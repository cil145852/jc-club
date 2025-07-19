package com.cjl.subject.domain.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-19-9:55
 * @Description
 */
@Component
@Slf4j
public class SyncLikedJob {

    @XxlJob("syncLikedJobHandler")
    public void syncLikedJobHandler() {
        XxlJobHelper.log("syncLikedJobHandler.run");
        try {
            log.info("123456");
        } catch (Exception e) {
            XxlJobHelper.log("syncLikedJobHandler.error:{}", e.getMessage(), e);
        }

    }
}
