package com.hanshun.yupicturebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2025/12/06 21:34:05
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许发送cookie
                .allowCredentials(true)
                // 放行哪些域名
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                // 后端响应头中哪些字段能被前端读取
                .exposedHeaders("*")
                .allowedHeaders("*");
    }
}
