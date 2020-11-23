package com.topov.estatesearcher.telegram.state.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMapping {
    String forCommand();
}
