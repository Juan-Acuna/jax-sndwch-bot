package xyz.sandwichbot.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String name();
	String desc() default "";
	String[] alias() default {};
	boolean enabled() default true;
	boolean visible() default true;
}
