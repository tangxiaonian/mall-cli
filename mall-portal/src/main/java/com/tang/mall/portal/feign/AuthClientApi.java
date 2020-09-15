package com.tang.mall.portal.feign;


import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.domain.Oauth2TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@FeignClient(value = "mall-auth")
public interface AuthClientApi {

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public CommonResult<Oauth2TokenDto> postAccessToken(Principal principal,
                                                        @RequestParam Map<String, String> parameters);

}
