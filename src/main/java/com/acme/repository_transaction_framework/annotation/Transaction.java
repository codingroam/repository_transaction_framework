package com.acme.repository_transaction_framework.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Transaction {

    String type() default "";
}
