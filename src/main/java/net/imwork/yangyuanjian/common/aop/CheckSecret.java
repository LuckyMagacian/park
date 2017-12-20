package net.imwork.yangyuanjian.common.aop;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.annotation.Secret;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.RetMessage;
import net.imwork.yangyuanjian.park.consts.Config;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

@Aspect
//@Component
public class CheckSecret {
    @Pointcut ("@annotation(net.imwork.yangyuanjian.common.annotation.Secret)")
    public void method(){}
    @Around("method()")
    public String check(ProceedingJoinPoint joinPoint){

            AopJob<String> job=(clazz, method, args, point)->{
                try {
                    Annotation level=method.getAnnotation(Secret.class);
                    if(level!=null){
                        HttpServletRequest req= (HttpServletRequest) Stream.of(args).filter(e->e instanceof HttpServletRequest).findAny().orElse(null);
                        String secret=req.getParameter("secret");
                        if(Config.secret.equals(secret)){
                            return (String) point.proceed(args);
                        }else
                            return  (String) new RetMessage(RetMessage.FAIL,"密钥验证不通过!",null).toJson();
                    }else{
                        return  (String) point.proceed(args);
                    }
                } catch (Throwable e) {
                    LogFactory.error(CheckSecret.class,"");
                    return  (String) new RetMessage(RetMessage.FAIL,"密钥验证不通过!",null).toJson();
                }
            };
            return  AopJob.workJob(job,joinPoint);

    }
}
