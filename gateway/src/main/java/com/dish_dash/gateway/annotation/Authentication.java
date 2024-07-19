package com.dish_dash.gateway.annotation;

import javax.persistence.Inheritance;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inheritance
@Documented
public @interface Authentication {}
