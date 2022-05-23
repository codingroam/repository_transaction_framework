package com.acme.repository_transaction_framework.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Table {
    String name();
    String key() default "";
}
