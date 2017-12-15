package net.imwork.yangyuanjian.common.assist;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by yangyuanjian on 2017/11/23.
 */
public interface ReflectAssist {
    /**
     * 获取aop目标类
     */
    static Class<?> getTargetClass(ProceedingJoinPoint point) {
        return point.getTarget().getClass();
    }

    /**
     * 获取aop目标方法参数值
     */
    static Object[] getArgs(ProceedingJoinPoint point) {
        return point.getArgs();
    }

    /**
     * 获取aop目标方法名称
     */
    static String getTargetMethodName(ProceedingJoinPoint point) {
        return point.getSignature().getName();
    }

    /**
     * 获取aop目标方法
     */
    static Method getTargetMethod(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    static String getEnvironmentClassName(Integer level) {
        if (level == null || level == 0)
            return getEnvironmentClassName(0);
        if (level < 0)
            throw new IllegalArgumentException("arg:level can't less then 0 !");
        return Thread.currentThread().getStackTrace()[level].getClassName();
    }

    static String getEnvironmentMethodName(Integer level) {
        if (level == null || level == 0)
            return getEnvironmentMethodName(0);
        if (level < 0)
            throw new IllegalArgumentException("arg:level can't less then 0 !");
        return Thread.currentThread().getStackTrace()[level].getMethodName();
    }


    static String getEnvironmentClassName() {
        //取调用者类名
        String className = getEnvironmentClassName(2);
        //若包含lambda说明是lambda表达式,取第三级
        if (className.contains("Lambda") || className.contains("lambda"))
            return getEnvironmentClassName(3);
            //若加载对应类发现是个接口,取第三级
        else try {
            if (Modifier.isInterface(Class.forName(className).getModifiers()))
                return getEnvironmentClassName(3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return className;
    }


    static String getEnvironmentMethodName() {
        //取调用的方法名
        String methodName = getEnvironmentMethodName(2);
        //若包含lambda说明是lambda表达式,取第三级
        if (methodName.contains("Lambda") || methodName.contains("lambda"))
            return getEnvironmentMethodName(3);
        return methodName;
    }

    static Map<String, Object> getMethodArgInfo(Method method) {
        Parameter[] params = method.getParameters();
        System.out.println(params[0].getClass());
        return null;
    }
}