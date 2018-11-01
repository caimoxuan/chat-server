package com.cmx.chatserver.security.config;

import com.cmx.chatserver.security.component.realm.UserRealm;
import com.cmx.chatserver.security.component.realm.matcher.PasswordCredentialsMatcher;
import com.cmx.chatserver.security.component.session.cache.RedisCacheManager;
import com.cmx.chatserver.security.component.session.dao.RedisSessionDao;
import com.cmx.chatserver.security.component.session.redis.RedisManager;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Value("${shiro.algorithm.name}")
    private String algorithmName;

    @Value("${shiro.hash.iterators}")
    private Integer hashIterator;


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //匹配所有请求 authc 需要验证， anon 不需要验证 这里的权限可以使用动态添加的方式
        filterChainDefinitionMap.put("/accountLogin", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        //其他所有的路径访问 要么的登入才能访问， 要么使用remember登入访问
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //配置自定义的拦截器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(simpleUserRealm());
        //设置sessionManager
        securityManager.setSessionManager(sessionManager());
        //设置cacheManager
        securityManager.setCacheManager(redisCacheManger());
        return securityManager;
    }

    @Bean
    public Realm simpleUserRealm(){
        UserRealm simpleUserRealm = new UserRealm();
        simpleUserRealm.setCredentialsMatcher(credentialsMatcher());
        simpleUserRealm.setAuthorizationCache(redisCacheManger().getCache("permissionCache"));
        return simpleUserRealm;
    }


    /**
     * 密码凭证匹配器
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher(){
        PasswordCredentialsMatcher credentialsMatcher = new PasswordCredentialsMatcher(redisCacheManger());
        credentialsMatcher.setHashAlgorithmName(algorithmName);
        credentialsMatcher.setHashIterations(hashIterator);
        //是否使用Hex方式 否则使用base64
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public SessionDAO redisSessionDao(){
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        redisSessionDao.setRedisManager(redisManager());
        return redisSessionDao;
    }

    /**
     * 使用redis作缓存
     * @return
     */
    @Bean
    public CacheManager redisCacheManger(){
        RedisCacheManager redisCacheManager =  new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(){
        return new RedisManager();
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDao());
//        List<SessionListener> list = new ArrayList<>();
//        defaultWebSessionManager.setSessionListeners(list);   //会话监听列表
        defaultWebSessionManager.setDeleteInvalidSessions(true); //是否删除过期的session
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);//设置过期的时间 毫秒为单位
        defaultWebSessionManager.setSessionIdCookie(sessionIdCookie());
        return defaultWebSessionManager;
    }

    /**
     * cookie 生成模板
     */
    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sid-CMX");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);// 30 天  -1 ：关闭浏览器失效
        return simpleCookie;
    }

}
