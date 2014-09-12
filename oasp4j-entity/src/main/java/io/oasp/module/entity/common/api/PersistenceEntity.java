/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.entity.common.api;

/**
 * This is the interface to mark a {@link Entity} of the <em>persistence</em> layer. Unlike
 * {@link io.oasp.module.entity.common.api.transferobject.TransferObject}s such object may be in {@link #STATE_MANAGED}
 * and is connected with a persistent store. Typically this will happen using JPA and an RDBMS. In this case classes
 * implementing this interface will have {@link javax.persistence.Entity JPA annotations} and gets
 * {@link #STATE_MANAGED managed} by an {@link javax.persistence.EntityManager}.<br/>
 * <b>ATTENTION:</b><br/>
 * An instance of this interface can NOT be considered as a regular java-object. It is better to think of a persistence
 * entity as a direct view into the persistent store. Therefore you should never make modifications (e.g. invoke setters
 * or sort list properties) that are NOT intended to be persisted.<br>
 * Further the underlying persistence-code may create dynamic proxies for your entity. Therefore you should NOT declare
 * methods (getters and setters) as <code>final</code> except you exactly know what you are doing. Additionally
 * {@link #getClass()} may return a subclass of your entity that you never created (see <code>PersistenceManager</code>
 * in <code>mmm-persistence</code> for additional information and support).</li>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceEntity extends Entity {

  /**
   * A {@link PersistenceEntity} is in the <em>new</em> (or transient) state when it is manually created via its
   * constructor (using <code>new</code>). In this state it can be considered as a regular java-object. Whenever
   * {@link javax.persistence.EntityManager#persist(Object) persist} or even
   * {@link javax.persistence.EntityManager#merge(Object) merge} is called, the state of the entity changes to
   * {@link #STATE_MANAGED managed}.
   */
  String STATE_NEW = "new";

  /**
   * A {@link PersistenceEntity} becomes <em>managed</em> when it is
   * {@link javax.persistence.EntityManager#persist(Object) persisted} or in case it is directly
   * {@link javax.persistence.EntityManager#find(Class, Object) retrieved} from the persistent store (database). It will
   * remain in the managed state until a state change is explicitly triggered via
   * {@link javax.persistence.EntityManager#detach(Object) detach} /
   * {@link javax.persistence.EntityManager#remove(Object) remove} or in case the transaction is committed. <br/>
   * <b>ATTENTION:</b><br/>
   * If managed entity is modified and the transaction gets committed, all modifications are written to the persistent
   * store. Be careful that you never make modifications that are NOT intended to be persisted.
   */
  String STATE_MANAGED = "managed";

  /**
   * If {@link javax.persistence.EntityManager#remove(Object) remove} is called on a {@link #STATE_MANAGED managed}
   * entity, its state changes to <code>removed</code> and the entity will be deleted from the persistent store whenever
   * the transaction is committed.
   */
  String STATE_REMOVED = "removed";

  /**
   * If {@link javax.persistence.EntityManager#detach(Object) detach} is called on a {@link #STATE_MANAGED managed}
   * entity, its state changes to <code>detached</code>. The same applies of the transaction is committed or on manual
   * invocations of {@link javax.persistence.EntityManager#clear() clear} or
   * {@link javax.persistence.EntityManager#close() close}.<br/>
   * If a relational property is {@link javax.persistence.FetchType#LAZY lazy} and has not been fetched previously,
   * calls to get this property will cause an exception. A detached entity can change its state back to
   * {@link #STATE_MANAGED managed} via {@link javax.persistence.EntityManager#merge(Object) merge}.
   */
  String STATE_DETACHED = "detached";

}
