package net.imwork.yangyuanjian.common.aop;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.ReflectAssist;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by thunderobot on 2017/11/18.
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SessionCharset {
    @Pointcut("@annotation(net.imwork.yangyuanjian.common.annotation.SetUtf8)")
//    @Pointcut("execution(* net.imwork.yangyuanjian.park..*.*(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse))")
    public void setUtf8(){}

    @Around("setUtf8()")
    public <T> T setUtf(ProceedingJoinPoint joinPoint){
        //获取代理方法参数值
        Object[] args=joinPoint.getArgs();
        Class clazz= joinPoint.getTarget().getClass();
        Method method= ReflectAssist.getTargetMethod(joinPoint);
        try {
            for(Object each:args){
                if(each instanceof HttpServletRequest){
                    HttpServletRequest request= (HttpServletRequest) each;
                    request.setCharacterEncoding("utf-8");

                }else if(each instanceof HttpServletResponse){
                    HttpServletResponse response= (HttpServletResponse) each;
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                    response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
                    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
                    response.setHeader("Access-Control-Allow-Origin", "*");
                }
            }
            LogFactory.info(clazz,"set  class:["+clazz.getName()+"]method:["+method.getName()+"] charset utf-8 !");
            return (T) joinPoint.proceed(args);
        }catch (Throwable throwable){
            throw new RuntimeException("occured exception when set charset class:["+clazz==null?null:clazz.getName()+"]method:["+method==null?null:method.getName()+"]",throwable);
        }
    }

}
