package com.acme.repository_transaction_framework.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Select{
    String value();
}
