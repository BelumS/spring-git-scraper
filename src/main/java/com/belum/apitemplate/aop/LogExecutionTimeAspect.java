package com.belum.apitemplate.aop;

import com.belum.apitemplate.aop.pointcut.AbstractPointCut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * Created by bel-sahn on 7/29/19
 */
@Aspect
@Component
public class LogExecutionTimeAspect extends AbstractPointCut {
    //region PROPERTIES
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //endregion

    //region HELPER METHODS
    @Around("allComponents()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        if (log.isTraceEnabled())
            log.trace("{}: Entering... ", pjp.toShortString());

        final Object result = pjp.proceed(pjp.getArgs());

        if (log.isDebugEnabled()) {
            long elapsedTime = System.currentTimeMillis() - start;
            log.debug("{}: Exiting ... ElapsedTime = {} ms", pjp.toShortString(), elapsedTime);
        }
        return result;
    }
    //endregion
}
