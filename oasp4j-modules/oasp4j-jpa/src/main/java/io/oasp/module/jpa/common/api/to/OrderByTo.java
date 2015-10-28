package io.oasp.module.jpa.common.api.to;

/**
 * Transfer object to transmit order criteria
 */
public class OrderByTo extends AbstractTo {

  private static final long serialVersionUID = 1L;

  private String name;

  private OrderDirection direction;

  /**
   * The constructor.
   */
  public OrderByTo() {

    super();
  }

  /**
   * Returns the field 'name'.
   *
   * @return Value of name
   */
  public String getName() {

    return this.name;
  }

  /**
   * Sets the field 'name'.
   *
   * @param name New value for name
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * Returns the field 'direction'.
   *
   * @return Value of direction
   */
  public OrderDirection getDirection() {

    return this.direction;
  }

  /**
   * Sets the field 'direction'.
   *
   * @param direction New value for direction
   */
  public void setDirection(OrderDirection direction) {

    this.direction = direction;
  }

  @Override
  protected void toString(StringBuilder buffer) {

    buffer.append(this.name);
    buffer.append(' ');
    buffer.append(this.direction);
  }

}
