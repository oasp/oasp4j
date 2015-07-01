package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport.writer;

import static com.google.common.collect.Lists.newArrayList;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * TODO ABIELEWI This type ...
 *
 * @author ABIELEWI
 */
public class BillCompositeWriter implements ResourceAwareItemWriterItemStream<BillCto>, InitializingBean {

  private ResourceAwareItemWriterItemStream<String> delegate;

  private LineAggregator<BillEto> billLineAggregator;

  private LineAggregator<OrderPositionEto> positionLineAggregator;

  private Resource resource;

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterPropertiesSet() throws Exception {

    this.delegate.setResource(this.resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {

    this.delegate.open(executionContext);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {

    this.delegate.update(executionContext);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws ItemStreamException {

    this.delegate.close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends BillCto> items) throws Exception {

    List<String> lines = newArrayList();
    for (BillCto item : items) {
      String billLine = this.billLineAggregator.aggregate(item.getBill());
      lines.add(billLine);
      for (OrderPositionEto position : item.getPositions()) {
        String positionLine = this.positionLineAggregator.aggregate(position);
        lines.add(positionLine);
      }
    }
    this.delegate.write(lines);
  }

  /**
   * {@inheritDoc}
   */

  @Override
  public void setResource(Resource resource) {

    this.resource = resource;
  }

  /**
   * @param delegate the delegate to set
   */
  public void setDelegate(ResourceAwareItemWriterItemStream<String> delegate) {

    this.delegate = delegate;
  }

  /**
   * @param billLineAggregator the billLineAggregator to set
   */
  public void setBillLineAggregator(LineAggregator<BillEto> billLineAggregator) {

    this.billLineAggregator = billLineAggregator;
  }

  /**
   * @param positionLineAggregator the positionLineAggregator to set
   */
  public void setPositionLineAggregator(LineAggregator<OrderPositionEto> positionLineAggregator) {

    this.positionLineAggregator = positionLineAggregator;
  }

}
