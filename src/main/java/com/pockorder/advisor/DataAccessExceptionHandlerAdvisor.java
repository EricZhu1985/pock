package com.pockorder.advisor;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pockorder.view.MsgResult;

@EnableWebMvc 
@ControllerAdvice(annotations = {RestController.class})  
public class DataAccessExceptionHandlerAdvisor {
	
    @ExceptionHandler(DataAccessException.class)  
    public MsgResult handleDataAccessException(Exception exception, WebRequest request) {
    	System.out.println("===================exception:" + exception.getMessage());
    	MsgResult msg = new MsgResult();
    	msg.setErrMsg("SQL异常！");
        return msg;  
    }  
}
