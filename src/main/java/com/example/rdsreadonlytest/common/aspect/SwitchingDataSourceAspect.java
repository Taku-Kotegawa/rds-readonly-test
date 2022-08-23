package com.example.rdsreadonlytest.common.aspect;

import com.example.rdsreadonlytest.domain.enums.DataSourceType;
import com.example.rdsreadonlytest.domain.model.DbContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Aspect
@Component
public class SwitchingDataSourceAspect {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        try {
            DataSourceType type = this.getDataSourceType(joinPoint, transactional);
            DbContextHolder.setDataSourceType(type);
            return joinPoint.proceed();
        } finally {
            DbContextHolder.clear();
        }
    }


    private DataSourceType getDataSourceType(JoinPoint joinPoint, Transactional transactional) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();

        Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);

        method.getAnnotation(Transactional.class);

        return null;
    }

}