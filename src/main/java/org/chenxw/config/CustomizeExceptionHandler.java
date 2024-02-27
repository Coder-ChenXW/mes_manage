package org.chenxw.config;

import lombok.extern.log4j.Log4j2;
import org.chenxw.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
	public Result<Object> exceptionHandler(Exception e){
        log.error(e);
		return Result.generateError(30001, e.getMessage());
    }
}