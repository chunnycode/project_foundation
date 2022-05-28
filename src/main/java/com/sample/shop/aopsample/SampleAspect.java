package com.sample.shop.aopsample;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SampleAspect {

	@Pointcut("execution(* com.sample.shop.web.login.RestLoginController.*(..))")
	private void doExecute() {}

	@Around("doExecute()")
	public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable{
		log.info("In dologging");

		String methodName = joinPoint.getSignature().toShortString();
		try {
			log.info(methodName+" is start");
			Object obj = joinPoint.proceed();
			return obj;
		}finally {
			log.info(methodName + " is Finish");
		}
	}

}
