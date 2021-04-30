package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(Options.class)
@Target({ FIELD, LOCAL_VARIABLE, METHOD })
public @interface Option {
	String name();
	String desc();
	String[] alias() default {};
	boolean enabled() default true;
	boolean visible() default true;
}
