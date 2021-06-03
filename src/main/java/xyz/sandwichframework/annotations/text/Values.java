package xyz.sandwichframework.annotations.text;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import xyz.sandwichframework.core.util.Language;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Values {
	Language value();
}
