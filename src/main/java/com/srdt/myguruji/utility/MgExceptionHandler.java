package com.srdt.myguruji.utility;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MgExceptionHandler {

	@Around("execution(@MgException * *.*(..)) && @annotation(MgException)")
    public Object handle(ProceedingJoinPoint pjp, MgException catcher) throws Throwable {
        try {
            // execute advised code
            return pjp.proceed();
        }
        catch (Throwable e) {
            // check exceptions specified in annotation contain thrown exception
            if (Arrays.stream(catcher.exceptions())
                      .anyMatch(klass -> e.getClass().equals(klass))) {
                // custom logic goes here
            }
            // exception wasn't specified, rethrow
            else {
                throw e;
            }
        }
		return catcher;
    }
	
}
