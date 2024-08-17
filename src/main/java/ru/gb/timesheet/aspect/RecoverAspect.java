package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class RecoverAspect {

    @Pointcut("@annotation(ru.gb.timesheet.aspect.Recover)") // method
    public void recoverMethodPointcut() {
    }

    @Pointcut("@within(ru.gb.timesheet.aspect.Recover)") // class
    public void recoverTypePointcut() {}



    @Around(value = "recoverMethodPointcut() || recoverTypePointcut()")
    public Object aroundServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method methodR = methodSignature.getMethod();
        boolean enabled = true;
        if (methodR.isAnnotationPresent(Recover.class)) {
            enabled = methodR.getAnnotation(Recover.class).enabled();
        } else if (target.getClass().isAnnotationPresent(Recover.class)) {
            enabled = target.getClass().getAnnotation(Recover.class).enabled();
        }
        if (enabled) {
            List<String> params = Arrays.stream(pjp.getArgs())
                    .filter(Objects::nonNull)
                    .map(u -> u.getClass().getSimpleName() + " = " + u.toString())
                    .toList();
            try {
                return pjp.proceed();
            } catch (Exception ex) {
                String serviceName = pjp.getTarget().getClass().getSimpleName();
                String methodName = pjp.getSignature().getName();
                log.info("{} # {} {} {}", serviceName, methodName, params, ex.getClass().getSimpleName());
                Method method = ((MethodSignature) pjp.getSignature()).getMethod();
                Object[] args = pjp.getArgs();
                args[0] = "1";
                pjp.proceed(args);
            }
        }
        return pjp.proceed();


    }
}
