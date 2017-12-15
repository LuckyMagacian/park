package net.imwork.yangyuanjian.common.aop;

import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
* Created by thunderobot on 2017/11/18.
*/
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+10)
public class AddLog {

    @Pointcut("@annotation(net.imwork.yangyuanjian.common.annotation.EasyLog)")
    public void easyLogMethod(){}
    @Pointcut("@within(net.imwork.yangyuanjian.common.annotation.EasyLog)")
    public void easyLogClass(){}

    @Around("easyLogClass()||easyLogMethod()")
    public <T> T logDefault(ProceedingJoinPoint joinPoint){
        AopJob<T> job = (clazz, method, args, point) -> {
            T t = null;
            try {
                LogFactory.LogLevel level = getLogLevel(method);
                log(clazz, "try invoke class:[" + clazz.getName() + "]method:[" + method + "]arg:[" + Arrays.asList(args) + "]", level);
                t = (T) joinPoint.proceed(args);
                //若是集合类型则记录返回的数量,否则记录返回结果
                if (t instanceof Collection) {
                    Collection collection = (Collection) t;
                    log(clazz, "class:[" + clazz.getName() + "]method:[" + method.getName() + "]resultSize:[" + collection.size() + "]arg:[" + Arrays.asList(args) + "]", level);
                    LogFactory.debug(clazz, "class:[" + clazz.getName() + "]method:[" + method.getName() + "]result:[" + collection + "]arg:[" + Arrays.asList(args) + "]");
                    return t;
                } else if (t instanceof Map) {
                    Map map = (Map) t;
                    log(clazz, "class:[" + clazz.getName() + "]method:[" + method.getName() + "]resultSize:[" + map.size() + "]arg:[" + Arrays.asList(args) + "]", level);
                    LogFactory.debug(clazz, "class:[" + clazz.getName() + "]method:[" + method.getName() + "]result:[" + map + "]arg:[" + Arrays.asList(args) + "]");
                    return t;
                } else {
                    log(clazz, "invoke class:[" + clazz.getName() + "]method:[" + method.getName() + "]arg:[" + Arrays.asList(args) + "]result:[" + t + "]", level);
                    return t;
                }
            } catch (Throwable throwable) {
                //记录异常
                LogFactory.error(clazz, "occured exception ["+throwable+"]!class:[" + clazz.getName() + "]method[" + method + "] arg:[" + Arrays.asList(args) + "]", throwable);
                return t;
            }
        };
        return AopJob.workJob(job, joinPoint);
    }

    public void log(Class clazz, String mesage, LogFactory.LogLevel level){
        log(clazz,mesage,null,level);
    }

    public void log(Class clazz, String message, Throwable e, LogFactory.LogLevel level){
        switch (level){
            case DEBUG  :logDebug(clazz,message,e);break;
            case INFO   :logInfo(clazz,message,e);break;
            case WARN   :logWarn(clazz,message,e);break;
            case ERROR  :logError(clazz,message,e);break;
            default     :logInfo(clazz,message,e);break;
        }
    }

    public void logDebug(Class clazz,String message,Throwable e){
        if(null==e)
            LogFactory.debug(clazz,message);
        else
            LogFactory.debug(clazz,message,e);
    }
    public void logInfo(Class clazz,String message,Throwable e){
        if(null==e)
            LogFactory.info(clazz,message);
        else
            LogFactory.info(clazz,message,e);
    }
    public void logWarn(Class clazz,String message,Throwable e){
        if(null==e)
            LogFactory.warn(clazz,message);
        else
            LogFactory.warn(clazz,message,e);
    }
    public void logError(Class clazz,String message,Throwable e){
        if(null==e)
            LogFactory.error(clazz,message);
        else
            LogFactory.error(clazz,message,e);
    }



    /**
     * 获取方法上的日志等级
     * @param method
     * @return
     */
    private LogFactory.LogLevel getLogLevel(Method method){
        Annotation level=method.getAnnotation(EasyLog.class);
        if(level==null){
            return getLogLevel(method.getDeclaringClass());
        }
        else
            return ((EasyLog) level).value();

    }

    /**
     * 获取类上的日志等级
     * @param clazz
     * @return
     */
    private LogFactory.LogLevel getLogLevel(Class clazz){
        Annotation level=clazz.getAnnotation(EasyLog.class);
        if(level==null)
            return LogFactory.LogLevel.INFO;
        else
            return ((EasyLog)level).value();
    }

//    /**
//     * 获取日志等级
//     * @param type
//     * @return
//     */
//    private LogFactory.LogLevel getLogLevel(GenericArrayType type){
//        if(type==null)
//            throw new IllegalArgumentException("arg: type can't be null !");
//        LogFactory.LogLevel level=null;
//        Object obj=type;
//        if(obj instanceof Class){
//            level=getLogLevel((Class) obj);
//        }
//        if(obj instanceof Method){
//            level=getLogLevel((Method)obj);
//        }
//        return level==null? LogFactory.LogLevel.INFO:level;
//    }

}


