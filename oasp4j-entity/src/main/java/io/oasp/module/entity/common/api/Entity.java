package io.oasp.module.entity.common.api;

import io.oasp.module.entity.common.api.transferobject.AbstractEto;

import java.io.Serializable;

/**
 * This is the interface for a (persistent) entity, which is an object that is potentially stored in a persistent store
 * (typically a database and via JPA). Every non-abstract implementation of this interface is simply called an
 * <em>entity</em>. It is supposed to be a simple java-bean.<br>
 * This interface makes the following assumptions:
 * <ul>
 * <li>A {@link Entity} is identified by a {@link #getId() primary key} of the type {@link Long}.</li>
 * <li>A {@link Entity} has a {@link #getVersion() version} that is a modification counter for optimistic locking.</li>
 * </ul>
 * An instance of this interface can be one of the following:
 * <ul>
 * <li>a <b>{@link PersistenceEntity}</b><br/>
 * <li>a <b>{@link AbstractEto entity transfer-object}</b><br/>
 * </ul>
 * In order to distinguish the above cases an application has an architecture that organizes the code in technical
 * layers (see <a href="http://en.wikipedia.org/wiki/Multilayered_architecture">multilayered architecture</a>) and
 * business oriented slices (business components). Therefore within the persistence layer instances should always be
 * {@link PersistenceEntity persistence entities} (independent from their actual state such as
 * {@link PersistenceEntity#STATE_MANAGED managed}). On the other hand in the higher layers instances always need to be
 * {@link io.oasp.module.entity.common.api.transferobject.TransferObject}s. Our recommendation is to convert (see
 * <code>BeanMapper</code>) persistent entities into {@link io.oasp.module.entity.common.api.transferobject.AbstractEto
 * entity transfer-objects} when exposing functionality in the API of the <em>logic</em> layer.<br/>
 * Further please note that transfer-objects for external services need to be separated from the actual entities so they
 * keep stable in case of internal changes.
 *
 * @see javax.persistence.Entity
 *
 * @author hohwille
 */
public interface Entity extends Serializable {

  /**
   * Gets the unique identifier of this object as {@link Long}. This is a technical ID that is typically
   * {@link javax.persistence.GeneratedValue auto-generated}. If you need a business oriented identifier of a different
   * type or with specific numbering you are suggested to add an additional property that you mark as
   * {@link javax.persistence.Column#unique() unique}. For your DAO and use-case you can add operations such as e.g.
   * <code>findByKey(String)</code> if your property was declared as <code>String getKey()</code>.
   *
   * @return the unique ID of this entity.
   */
  Long getId();

  /**
   * @return the {@link javax.persistence.Version} used for optimistic locking. Incremented on transaction commit with
   *         every modification of the entity.
   */
  int getVersion();

}
