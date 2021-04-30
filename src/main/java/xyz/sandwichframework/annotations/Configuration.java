package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xyz.sandwichframework.core.util.Language;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
	Language value();
}
