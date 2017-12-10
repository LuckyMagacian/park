package net.imwork.yangyuanjian.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        /** Class, interface (including annotation type), or enum declaration */
        TYPE,
        /** Field declaration (includes enum constants) */
        FIELD,
        /** Method declaration */
        METHOD,
        /** Formal parameter declaration */
        PARAMETER,
        /** Constructor declaration */
        CONSTRUCTOR,
        /** Local variable declaration */
        LOCAL_VARIABLE,
        /** Annotation type declaration */
        ANNOTATION_TYPE,
        /** Package declaration */
        PACKAGE,
        TYPE_PARAMETER,
        TYPE_USE
})
public @interface Comment {
    String value();
}
