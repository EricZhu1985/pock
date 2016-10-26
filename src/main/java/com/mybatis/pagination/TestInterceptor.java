package com.mybatis.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({@Signature(type=Executor.class,method="query",args={ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })})  
public class TestInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		//当前环境 MappedStatement，BoundSql，及sql取得  
	    MappedStatement mappedStatement= (MappedStatement) invocation.getArgs()[0];      
	    Object parameter = invocation.getArgs()[1];   
	    BoundSql boundSql = mappedStatement.getBoundSql(parameter);   
	    String originalSql = boundSql.getSql().trim();  
	    Object parameterObject = boundSql.getParameterObject(); 
	    
	    RowBounds rowBound = (RowBounds) invocation.getArgs()[2];
	    if(Pager.class.equals(rowBound.getClass())) {
		    System.out.println("======== Page Interceptor: " + invocation.getTarget().getClass().getName() + "(" + invocation.getMethod().getName() + ") =========");
	    	Pager pager = (Pager) rowBound;
	    	
	    	String countSql = getCountSql(originalSql);  
	        Connection connection=mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection()  ;            
	        PreparedStatement countStmt = connection.prepareStatement(countSql);    
	        BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);  
	        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);  
	        parameterHandler.setParameters(countStmt);  
	        ResultSet rs = countStmt.executeQuery();
	        int total = 0;  
	        if (rs.next()) {    
	        	total = rs.getInt(1);    
	        }  
	        rs.close();    
	        countStmt.close();    
	        connection.close(); 
	    	
	    	pager.setTotal(total);
	    }
	    
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
	/** 
	   * 复制BoundSql对象 
	   */  
	  private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {  
	    BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
	    for (ParameterMapping mapping : boundSql.getParameterMappings()) {  
	        String prop = mapping.getProperty();  
	        if (boundSql.hasAdditionalParameter(prop)) {  
	            newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));  
	        }  
	    }  
	    return newBoundSql;  
	  }  
	  
	  /** 
	   * 根据原Sql语句获取对应的查询总记录数的Sql语句 
	   */  
	  private String getCountSql(String sql) {  
	    return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";  
	  }  

}
