package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Category {
	String name() default "NoID";
	String desc() default "NoDesc";
	boolean nsfw() default false;
	boolean visible() default true;
	boolean isSpecial() default false;
}
