package net.imwork.yangyuanjian.common.annotation;

import net.imwork.yangyuanjian.common.assist.LogFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by thunderobot on 2017/11/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface EasyLog {
    LogFactory.LogLevel value();
}
