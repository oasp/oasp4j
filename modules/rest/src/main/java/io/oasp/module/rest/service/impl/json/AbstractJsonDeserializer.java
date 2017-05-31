package io.oasp.module.rest.service.impl.json;

import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Helper class to simplify implementation of {@link JsonDeserializer}.
 *
 * @param <T> the class to be deserialized
 * @deprecated use {@link io.oasp.module.json.common.base.AbstractJsonDeserializer} instead.
 */
@Deprecated
public abstract class AbstractJsonDeserializer<T> extends io.oasp.module.json.common.base.AbstractJsonDeserializer<T> {

}
