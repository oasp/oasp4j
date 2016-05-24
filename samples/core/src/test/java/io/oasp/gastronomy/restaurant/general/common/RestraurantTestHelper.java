package io.oasp.gastronomy.restaurant.general.common;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 */

public class RestraurantTestHelper {

  @Inject
  private Flyway flyway;

  /**
   * The constructor.
   */
  public RestraurantTestHelper() {
    super();
  }

  public void login(String name) {

    // TODO implement in later iteration
  }

  public void logout() {

    // TODO implement in later iteration
  }

  public void dropAllH2Tables() throws IllegalStateException {
    // TODO implement in later iteration

  }

  public void dropH2() {

    // TODO implement in later iteration
  }

  public void resetH2() {
    // TODO implement in later iteration

  }

}
