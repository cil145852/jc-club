package com.cjl.auth.api;

import com.cjl.auth.entity.AuthUserDTO;
import com.cjl.auth.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-15:12
 * @Description
 */

@FeignClient(name = "jc-club-auth")
public interface UserFeignService {

   @PostMapping("/user/getUserInfo")
   Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO);
}
