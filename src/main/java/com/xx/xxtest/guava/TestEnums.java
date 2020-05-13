package com.xx.xxtest.guava;

import com.google.common.base.Enums;
import com.google.common.base.Optional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.EnumSet;

/**
 * @author xuxiang
 * 2020/5/13
 */
public class TestEnums {
    public static void main(String[] args) {

        // Get Filed obj of enum.
        Field field = Enums.getField(Sport.GOLF);
        System.out.println(field.getAnnotation(Description.class).value());

        // using valueOf method.
        // using guava Optional
        Sport enum1 = Enums.getIfPresent(Sport.class, "GOLF").or(Sport.SLEEP);
        System.out.println(enum1.name());
        Sport enum2 = Enums.getIfPresent(Sport.class, "NOT FOUND").or(Sport.SLEEP);
        System.out.println(enum2.name());


        /**
         * Other test
         */
        // loop enums.
        // using class
        EnumSet.allOf(Sport.class).forEach((e) -> System.out.println(e.name()));
        // using Class
        for (Sport value : Sport.values()) {
            System.out.println(value.name());
        }

    }

    /**
     * Sport enum
     */
    enum Sport {
        /**
         * golf
         */
        @Description("Push the ball to the hole.")
        GOLF,
        /**
         * sleep
         */
        SLEEP;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Description {
        String value() default "";
    }
}
