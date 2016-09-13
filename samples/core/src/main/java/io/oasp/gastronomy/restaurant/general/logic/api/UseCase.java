package io.oasp.gastronomy.restaurant.general.logic.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * This is a {@link Qualifier} to mark all use-cases. In your use-case implementation add the annotations
 * {@link javax.inject.Named} and {@link UseCase}. In your component facade implementation add the annotations
 * {@link javax.inject.Inject} and {@link UseCase} to the setters in order to inject your use case implementations.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface UseCase {

}
