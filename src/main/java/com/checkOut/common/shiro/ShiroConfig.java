package com.checkOut.common.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Shiro配置中心
 *
 * @author: Raymee
 * @E-mail: leohaoo@vip.qq.com
 * @date: 2017年5月14日 下午5:00:19
 * @version: 1.0
 * @since:
 */
@Configuration
@AutoConfigureAfter(ShiroSessionListener.class)
public class ShiroConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 配置shiro过滤器
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(manager);

        // 配置登录的url和登录成功的url
        shiroFilterFactoryBean.setLoginUrl("/sys/login");
        shiroFilterFactoryBean.setSuccessUrl("/sys/toWelcome");
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/login");

        // 配置访问权限
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/sys/doLogin", "anon");
        filterChainDefinitionMap.put("/sys/randcode", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/script/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fund/instanceFundPool", "anon");//全量更新备选基金池入口
        filterChainDefinitionMap.put("/fund/installFunctionAll", "anon");//全量更新精选基金池入口
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    // 配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm, @Qualifier("sessionManager") SessionManager sessionManager,
                                              @Qualifier("cacheManager") EhCacheManager cacheManager) {
        logger.info("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm);
        manager.setSessionManager(sessionManager);
        manager.setCacheManager(cacheManager);
        return manager;
    }

    // 配置自定义的权限登录器
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

    // 配置会话管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(@Qualifier("simpleCookie") SimpleCookie simpleCookie, @Qualifier("sessionDAO") SessionDAO sessionDAO, @Qualifier(value = "shiroSessionListener") ShiroSessionListener shiroSessionListener) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setGlobalSessionTimeout(3600000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionListeners(CollectionUtils.asList(shiroSessionListener));
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }

    // Shiro管理的Cookie
    @Bean(name = "simpleCookie")
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    // 配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        return credentialsMatcher;
    }

    // 配置缓存管理器
    @Bean(name = "cacheManager")
    public EhCacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }

    // 配置ShiroSessionDao
    @Bean(name = "sessionDAO")
    public SessionDAO sessionDao() {
        return new MemorySessionDAO();
    }

    // 注册Shiro的会话监听器
    @Bean(name = "shiroSessionListener")
    public ShiroSessionListener shiroSessionListener() {
        return new ShiroSessionListener();
    }

    // 让thymeleaf模板支持shiro标签
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
