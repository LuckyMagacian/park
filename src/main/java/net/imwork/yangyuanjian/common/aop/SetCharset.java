package net.imwork.yangyuanjian.common.aop;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
@Order(-10)
public class SetCharset {
    @Pointcut("@within(net.imwork.yangyuanjian.common.annotation.SetUtf8)")
    public void setCharset(){}

    @Around("setCharset()")
    public void setUtf(ProceedingJoinPoint joinPoint){
        //获取代理方法参数值
        Object[] args=joinPoint.getArgs();
        Class clazz= joinPoint.getTarget().getClass();
        Method method=null;
        try {
            for(Object each:args){
                if(each instanceof HttpServletRequest){
                    HttpServletRequest request= (HttpServletRequest) each;
                    request.setCharacterEncoding("utf-8");
                }else if(each instanceof HttpServletResponse){
                    HttpServletResponse response= (HttpServletResponse) each;
                    response.setCharacterEncoding("utf-8");
                }
            }
            LogFactory.info(clazz,"set  class:["+clazz.getName()+"]\nmethod:["+method.getName()+"] charset utf-8 !");
        }catch (Throwable throwable){
            throw new RuntimeException("occured exception when set charset class:["+clazz.getName()+"]\nmethod:["+method.getName()+"]",throwable);
        }
    }
}
