package io.oasp.module.entity.common.api.transferobject;

/**
 * This is the abstract base class for an {@link TransferObject} that only contains data without relations. This is
 * called <em>DTO</em> (data transfer object). Here data means properties that typically represent a datatype and
 * potentially for relations the ID (typically as {@link Long}). For actual relations you will use {@link AbstractCto
 * CTO}s to express what set of entities to transfer, load, save, update, etc. without redundancies. It often
 * corresponds to an {@link io.oasp.module.entity.common.api.Entity entity}. In such case use {@link AbstractEto}.
 * Otherwise if you derive from this class rather than {@link AbstractEto} use the suffix <code>Dto</code>.<br/>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractDto extends AbstractTransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

}
