package io.oasp.module.entity.common.api.transferobject;

/**
 * This is the abstract base class for a composite {@link TransferObject}. Such object should contain (aggregate) other
 * {@link TransferObject}s but no atomic data. This means it has properties that contain a {@link TransferObject} or a
 * {@link java.util.Collection} of those but no values ({@link String}, {@link Long}, etc.).<br/>
 * Classes extending this class should carry the suffix <code>Cto</code>.<br/>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractCto extends AbstractTransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

}
