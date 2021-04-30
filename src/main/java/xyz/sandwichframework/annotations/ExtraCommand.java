package xyz.sandwichframework.annotations;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ METHOD, FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtraCommand {
	String name();
}
