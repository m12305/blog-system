package com.my_project.bolg_system.exception;


import com.my_project.bolg_system.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    public Result handler(Exception e){
        log.error("发生异常, e: {}", e);
        return Result.fail(e.getMessage());
    }
}
