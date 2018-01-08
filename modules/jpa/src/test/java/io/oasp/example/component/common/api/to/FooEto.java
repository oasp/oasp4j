package io.oasp.example.component.common.api.to;

import io.oasp.example.component.common.api.Bar;
import io.oasp.example.component.common.api.Foo;
import io.oasp.module.basic.common.api.reference.IdRef;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * Implementation of {@link Foo} as {@link AbstractEto ETO}.
 */
public class FooEto extends AbstractEto implements Foo {
  private static final long serialVersionUID = 1L;

  private String name;

  private IdRef<Bar> barId;

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @Override
  public IdRef<Bar> getBarId() {

    return this.barId;
  }

  @Override
  public void setBarId(IdRef<Bar> barId) {

    this.barId = barId;
  }

}
