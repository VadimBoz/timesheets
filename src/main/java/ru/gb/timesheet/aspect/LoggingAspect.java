package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Slf4j // Slf4j - Simple logging facade for java
@Aspect
@Component
public class LoggingAspect {

  // Before
  // AfterThrowing
  // AfterReturning
  // After = AfterReturning + AfterThrowing
  // Around ->

//  Bean = TimesheetService, obj = timesheetService
  // proxyTimesheetService(obj)

//  @Pointcut("execution(* ru.gb.timesheet.service.TimesheetService.*(..))")
//  public void timesheetServiceMethodsPointcut() {
//  }
  @Pointcut("execution(* ru.gb.timesheet.service.*.*(..))")
  public void serviceMethodsPointcut() {
  }


  // Pointcut - точка входа в аспект
//  @Before(value = "timesheetServiceMethodsPointcut()")
////  @Before(value = "")
//  public void beforeTimesheetServiceFindById(JoinPoint jp) {
//    String methodName = jp.getSignature().getName();
//    var args = jp.getArgs();
//   List<String> params = Arrays.stream(jp.getArgs())
//           .filter(Objects::nonNull)
//           .map(u -> u.getClass().getSimpleName() + " = " + u.toString())
//            .toList();
//    log.info("Before -> TimesheetService#{} parametrs: {}", methodName, params);
//  }

  @Before(value = "serviceMethodsPointcut()")
//  @Before(value = "")
  public void beforeServiceMethods(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
    String serviceName = jp.getTarget().getClass().getSimpleName();
    List<String> params = Arrays.stream(jp.getArgs())
            .filter(Objects::nonNull)
            .map(u -> u.getClass().getSimpleName() + " = " + u.toString())
            .toList();
    log.info("Before -> {}# {} whith parametrs: {}",serviceName, methodName, params);
  }







//  @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
//  public void afterTimesheetServiceFindById(JoinPoint jp, Exception ex) {
//    String methodName = jp.getSignature().getName();
//    log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
//  }

}
