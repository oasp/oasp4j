package io.oasp.module.entity.common.api.transferobject;

import java.io.Serializable;

/**
 * This is the interface for a transfer-object. Such object is a {@link java.io.Serializable serializable} Java bean
 * used to transfer data between application layers (e.g. from <em>logic</em> to <em>client</em> layer). For more
 * information see the <a href="http://www.corej2eepatterns.com/Patterns2ndEd/TransferObject.htm">Transfer Object
 * Pattern</a>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransferObject extends Serializable {

}
