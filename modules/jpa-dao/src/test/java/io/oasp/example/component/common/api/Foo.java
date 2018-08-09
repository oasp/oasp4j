package io.oasp.example.component.common.api;

import io.oasp.example.general.common.api.TestApplicationEntity;
import io.oasp.module.basic.common.api.reference.IdRef;

public interface Foo extends TestApplicationEntity {

  String getName();

  void setName(String name);

  IdRef<Bar> getBarId();

  void setBarId(IdRef<Bar> barId);

}
