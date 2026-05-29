package com.my_project.bolg_system.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 同意响应返回
 * 所有接口都会被拦截并统一返回结果，如果有需要排除的response应该再做额外处理，需要优化
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        //可限定具体的拦截接口
        // // 仅处理com.blog.controller包下的接口
        //String className = returnType.getDeclaringClass().getName();
        //return className.startsWith("com.blog.controller");

        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result){
            return body;
        }
        if (body instanceof String){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(Result.success(body));
        }
        return Result.success(body);
    }
}
