package com.tang.mall.common.log;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AspectLog
 * @Description [ 日志切面类 ]
 * @Author Tang
 * @Date 2020/9/24 22:26
 * @Created by ASUS
 */
@Component
@Aspect
public class WebAspectLog {

    private Logger loggerFactory = LoggerFactory.getLogger(WebAspectLog.class);

    @Resource
    public HttpServletRequest servletRequest;

    @Pointcut("execution(public * com.tang.mall.*.controller.*.*(..))")
    public void pointCut() {}

    @Around(value = "pointCut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {

        Long startTime = System.currentTimeMillis();

        WebLog webLog = new WebLog();
        webLog.setStartTime(new Date());
        webLog.setRequestType(servletRequest.getMethod());
        webLog.setUrl(servletRequest.getRequestURL().toString());
        webLog.setThrowable(null);

        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();

        Object[] args = proceedingJoinPoint.getArgs();

        Signature signature = proceedingJoinPoint.getSignature();

        webLog.setClassName(signature.getDeclaringTypeName());
        webLog.setMethodName(signature.getName());

        Map<String, Object> parameterMap = new HashMap<>();
        Object result = null;

        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        Parameter parameter = null;
        for (int i = 0; i < args.length; i++,parameter = parameters[i]) {
            ApiIgnore apiIgnore = parameter.getAnnotation(ApiIgnore.class);
            if (apiIgnore == null) {
                String parameterName = parameter.getName();
                System.out.println("参数名:" + parameterName + ",参数值:" + args[i]);
                parameterMap.put(parameterName, args[i]);
            }
        }
        // 入参--->参数列表
        webLog.setParameters(parameterMap);
        try {
            result = proceedingJoinPoint.proceed(args);
            webLog.setResult(result);
        } catch (Throwable throwable) {
            webLog.setThrowable(throwable);
            webLog.setStatus("ERROR");
        }finally {
            webLog.setEndTime(new Date());
            webLog.setSpeed(System.currentTimeMillis() - startTime);
            String jsonString = JSONObject.toJSONString(webLog);
            if ("ERROR".equals(webLog.getStatus())) {
                loggerFactory.error(jsonString,webLog.getThrowable());
            }else {
                loggerFactory.info(jsonString);
            }
        }
        return result;
    }

}