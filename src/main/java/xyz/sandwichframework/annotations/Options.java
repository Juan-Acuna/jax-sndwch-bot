package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * Requerido por otras anotaciones.
 * Required by others annotations.
 * @author Juan Acuña
 * @version 1.0
 */
public @interface Options {
	Option[] value();
}
