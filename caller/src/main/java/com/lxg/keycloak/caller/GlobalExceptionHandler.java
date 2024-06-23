package com.lxg.keycloak.caller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex, WebRequest request) {
        // 记录日志或执行其他操作
        return new ResponseEntity<>("Runtime error message: "+ ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST); // 返回自定义错误消息和状态码
    }

}


