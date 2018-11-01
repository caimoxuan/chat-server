package com.cmx.chatserver.security.controller;

import com.cmx.chatserver.common.ResultData;
import com.cmx.chatserver.security.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class LoginController {


    @RequestMapping(value = "/accountLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultData login(String username,
                            String password,
                            String rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
        }catch(Exception e) {
            if(e instanceof LockedAccountException){
                log.info("登入次数过多，账户被锁定!");
                return ResultData.newFail(ErrorEnum.LOCKED_USER);
            }else if(e instanceof UnknownAccountException){
                log.info("账户不存在或用户名密码未填写");
                return ResultData.newFail(ErrorEnum.USER_NOT_EXIT);
            }else if(e instanceof AuthenticationException){
                log.info("认证失败, {}", e.getMessage());
                return ResultData.newFail(ErrorEnum.AUTH_FAIL);
            }else{
                log.info("other error : {}", e);
                return ResultData.newFail(ErrorEnum.UNKNOWN_SYSTEM_ERROR);
            }
        }
        return ResultData.newSingleSuccess("success");
    }


}
