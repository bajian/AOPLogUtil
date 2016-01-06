/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.aspect;

import org.android10.gintonic.internal.DebugLog;
import org.android10.gintonic.internal.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

    private static final String POINTCUT_METHOD =
            "execution(@org.android10.gintonic.annotation.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@org.android10.gintonic.annotation.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodBody = methodSignature.getName();
        String returnType= methodSignature.getReturnType().toString();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        methodBody+=getParams(joinPoint);

        String returnResult = "void";
        if (!returnType.equals("void"))
            returnResult=result.toString();

        DebugLog.log(className, buildLogMessage(returnType,methodBody,returnResult,stopWatch.getTotalTimeMillis()));

        return result;
    }

    private String getParams(ProceedingJoinPoint joinPoint){
        String re="";
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder(" ");
        builder.append('(');
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append('=');
            builder.append(parameterValues[i].toString());
        }
        builder.append(')');
        re = builder.toString();
        return re;
    }

    /**
     * Create a log message.
     *
     * @param methodDuration Duration of the method in milliseconds.
     * @param params A string with the method name and params.
     * @return A string representing message.
     */
    private static String buildLogMessage(String returnType,String params,String result, long methodDuration) {

        return "LogTrace: --> " +
                returnType +
                " " +
                params+
                " result="+result+
                " [" +
                methodDuration +
                "ms" +
                "]";
    }
}
