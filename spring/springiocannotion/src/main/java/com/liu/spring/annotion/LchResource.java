package com.liu.spring.annotion;
import java.lang.annotation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LchResource {

}
