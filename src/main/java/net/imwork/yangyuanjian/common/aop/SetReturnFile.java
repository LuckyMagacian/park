package net.imwork.yangyuanjian.common.aop;

import net.imwork.yangyuanjian.common.annotation.ReturnFile;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.enums.HttpReturnType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by thunderobot on 2017/11/18.
 */
@Aspect
//@Component
public class SetReturnFile {
    @Pointcut("@within(net.imwork.yangyuanjian.common.annotation.ReturnFile)")
    public void returnFile(){}

    @Around("returnFile()")
    public void getFile(ProceedingJoinPoint joinPoint){
        //获取代理方法参数值
        Object[] args=joinPoint.getArgs();
        Class clazz= joinPoint.getTarget().getClass();
        Method method=null;
        try {
            //获取代理方法对象
            method=((MethodSignature)joinPoint.getSignature()).getMethod();
            HttpReturnType type=getReturnType(method);
            for(Object each:args){
               if(each instanceof HttpServletResponse){
                    HttpServletResponse response= (HttpServletResponse) each;
                    response.setContentType(type.getHttpContentType());
                }
            }
            LogFactory.info(clazz,"set  class:["+clazz.getName()+"]\nmethod:["+method.getName()+"] return file !");
        }catch (Throwable throwable){
            throw new RuntimeException("occured exception when set return file, class:["+clazz.getName()+"]\nmethod:["+method.getName()+"]",throwable);
        }
    }

    /**
     * 获取方法上的日志等级
     * @param method
     * @return
     */
    private HttpReturnType getReturnType(Method method){
        Annotation type=method.getAnnotation(ReturnFile.class);
        if(type!=null){
            return ((ReturnFile) type).value();
        }else
            return null;
    }
}
