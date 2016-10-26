package com.pockorder.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.service.LogService;

/**
 * 日志记录，添加、删除、修改方法AOP
 * @author HotStrong
 * 
 */
@Aspect
public class RestControlLogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(RestControlLogAspect.class); 
	
	@Autowired
	private LogService logService;//日志记录Service

	@Autowired
	private HttpServletRequest request;
	
	
	/**
	 * 添加业务逻辑方法切入点
	 */
    @Pointcut("execution(@com.pockorder.annotation.Logged * *(..))")
    public void restControl() { }
    
    /**
     * 管理员添加操作日志(后置通知)
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
	@AfterReturning(value="restControl()", argNames="rtv", returning="rtv")
    public void restControlCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
		
		//判断参数
		if(joinPoint.getArgs() == null){//没有参数
			return;
		}
		
		//获取方法名
		String methodName = joinPoint.getSignature().getName();
		
		//获取操作内容
		String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
		
		logService.log(request, opContent);
		logger.info("test logger");
	}
	
	
	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值，
	 * 将参数值拼接为操作内容
	 */
	public String adminOptionContent(Object[] args, String mName) throws Exception{

		if (args == null) {
			return null;
		}
		
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			if(info != null) {
				//获取对象类型
				className = info.getClass().getName();
				className = className.substring(className.lastIndexOf(".") + 1);
				rs.append("(" + index + ":" + info.toString());
				rs.append(")");
			} else {
				rs.append("(" + index + ": null)");
			}
			index++;
		}
		
		return rs.toString();
	}
	
}
