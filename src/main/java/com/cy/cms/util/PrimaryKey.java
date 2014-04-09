package com.cy.cms.util;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface PrimaryKey {
}
