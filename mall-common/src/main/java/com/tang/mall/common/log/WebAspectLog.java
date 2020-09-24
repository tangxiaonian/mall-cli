package com.tang.mall.common.log;

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
import java.util.Arrays;
import java.util.Optional;

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
    public Object aroundMethod(JoinPoint joinPoint) {

        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;

        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();

        Object[] args = proceedingJoinPoint.getArgs();

        Object result = null;

        try {
            Method method = methodSignature.getMethod();
            Parameter[] parameters = method.getParameters();
            Arrays.stream(parameters).forEach((parameter -> {
                ApiIgnore apiIgnore = parameter.getAnnotation(ApiIgnore.class);
                if (apiIgnore == null) {
                    String parameterName = parameter.getName();
                    String simpleName = parameter.getType().getSimpleName();
                    System.out.println("参数名:" + parameterName + ",参数类型:" + simpleName);
                }
            }));
            result = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.getMessage();
            throwable.printStackTrace();
        }
        return result;
    }


}