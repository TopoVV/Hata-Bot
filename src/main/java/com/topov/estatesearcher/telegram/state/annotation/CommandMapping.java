package com.topov.estatesearcher.telegram.state.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CommandMapping {
    String forCommand();
}
