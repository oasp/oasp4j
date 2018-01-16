package io.oasp.example.component.common.api;

import io.oasp.example.general.common.api.TestApplicationEntity;

public interface Foo extends TestApplicationEntity {

  String getMessage();

  void setMessage(String message);

}
