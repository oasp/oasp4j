package io.oasp.gastronomy.restaurant.tablemanagement.common.builders;

import io.oasp.gastronomy.restaurant.general.common.P;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;

public class TableEntityBuilder {

  private List<P<TableEntity>> parameterToBeApplied;

  public TableEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<P<TableEntity>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

    number(1l);
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  public TableEntityBuilder number(final Long number) {

    this.parameterToBeApplied.add(new P<TableEntity>() {
      @Override
      public void apply(TableEntity target) {

        target.setNumber(number);
      }
    });
    return this;
  }

  public TableEntityBuilder waiterId(final Long waiterId) {

    this.parameterToBeApplied.add(new P<TableEntity>() {
      @Override
      public void apply(TableEntity target) {

        target.setWaiterId(waiterId);
      }
    });
    return this;
  }

  public TableEntityBuilder state(final TableState state) {

    this.parameterToBeApplied.add(new P<TableEntity>() {
      @Override
      public void apply(TableEntity target) {

        target.setState(state);
      }
    });
    return this;
  }

  public TableEntity createNew() {

    TableEntity tableentity = new TableEntity();
    for (P<TableEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(tableentity);
    }
    return tableentity;
  }

  public TableEntity persist(TransactionTemplate txt, final EntityManager em) {

    TableEntity tableentity = txt.execute(new TransactionCallback<TableEntity>() {
      /**
       * {@inheritDoc}
       */
      @Override
      public TableEntity doInTransaction(TransactionStatus status) {

        TableEntity tableentity = createNew();
        em.persist(tableentity);
        return tableentity;
      }
    });
    return tableentity;
  }

  public List<TableEntity> persistAndDuplicate(TransactionTemplate txt, final EntityManager em, final int quantity) {

    List<TableEntity> tableentityList = txt.execute(new TransactionCallback<List<TableEntity>>() {
      /**
       * {@inheritDoc}
       */
      @Override
      public List<TableEntity> doInTransaction(TransactionStatus status) {

        List<TableEntity> tableentityList = Lists.newLinkedList();
        for (int i = 0; i < quantity; i++) {
          TableEntity tableentity = createNew();
          tableentity.setNumber(tableentity.getNumber() + i);
          em.persist(tableentity);
          tableentityList.add(tableentity);
        }
        return tableentityList;
      }
    });

    return tableentityList;
  }
}
