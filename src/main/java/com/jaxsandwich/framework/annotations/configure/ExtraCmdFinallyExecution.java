package com.jaxsandwich.framework.annotations.configure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * [ES] Identifica el metodo que se ejecutara cuando el ciclo de vida de su comando extra finalice.<br>
 * [EN] Identifies the method wich will run when its extra command life cycle ends.
 * @author Juan Acuña
 * @version 1.1<br>
 * [ES] Requiere ser usado en una clase con la anotacion {@link ExtraCommandContainer}.<br>
 * [EN] Requires be used in a class with the annotation {@link ExtraCommandContainer}.
 */
public @interface ExtraCmdFinallyExecution {
	String name();
}
