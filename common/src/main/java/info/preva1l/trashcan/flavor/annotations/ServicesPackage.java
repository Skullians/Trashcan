package info.preva1l.trashcan.flavor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define the packages to search for services in.
 * <b>This annotation only works when put on an extended BasePlugin</b>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServicesPackage {
    String[] value();
}
