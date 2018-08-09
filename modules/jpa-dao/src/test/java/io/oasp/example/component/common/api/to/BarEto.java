package io.oasp.example.component.common.api.to;

import io.oasp.example.component.common.api.Bar;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * Implementation of {@link Bar} as {@link AbstractEto ETO}.
 */
public class BarEto extends AbstractEto implements Bar {

  private static final long serialVersionUID = 1L;

  private String message;

  @Override
  public String getMessage() {

    return this.message;
  }

  @Override
  public void setMessage(String message) {

    this.message = message;
  }

}
