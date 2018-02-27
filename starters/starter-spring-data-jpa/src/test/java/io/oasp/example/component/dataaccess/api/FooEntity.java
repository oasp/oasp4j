package io.oasp.example.component.dataaccess.api;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.oasp.example.component.common.api.Foo;
import io.oasp.example.general.dataaccess.api.TestApplicationPersistenceEntity;

/**
 * Implementation of {@link Foo} as {@link TestApplicationPersistenceEntity persistence entity}.
 */
@Entity
@Table(name = "Bar")
public class FooEntity extends TestApplicationPersistenceEntity implements Foo {
  private static final long serialVersionUID = 1L;

  private String message;

  private String name;

  @Override
  public String getMessage() {

    return this.message;
  }

  @Override
  public void setMessage(String message) {

    this.message = message;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

}
