package com.cmx.chatserver.security.component.realm;

import com.cmx.chatserver.security.dao.UserDao;
import com.cmx.chatserver.security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Slf4j
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserDao userDao;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("进行权限查询");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String)token.getPrincipal();
        Object password = token.getCredentials();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new UnknownAccountException("账号或密码不能为空");
        }
        User queryUser = new User(userName);
        User user = userDao.query(queryUser);
        if(null == user){
            throw new UnknownAccountException("未知的账号");
        }

        if(user.getLocked()){
            throw new LockedAccountException("锁定的账号");
        }
        //param1： principal
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return authenticationInfo;
    }
}
