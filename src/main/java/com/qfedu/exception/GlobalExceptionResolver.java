package com.qfedu.exception;

import com.qfedu.common.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//必须使用该注解（全局异常控制类）
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
public class GlobalExceptionResolver {
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e){
        return new JsonResult(1,e.getMessage());
    }
}
