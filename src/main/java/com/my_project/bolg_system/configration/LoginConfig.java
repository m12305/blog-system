package com.my_project.bolg_system.configration;

import com.my_project.bolg_system.interceptor.LoginInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器配置
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterception loginInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterception)
                .addPathPatterns("/user/**", "/blog/**")
                .excludePathPatterns("/user/login","/user/register");
    }
}
