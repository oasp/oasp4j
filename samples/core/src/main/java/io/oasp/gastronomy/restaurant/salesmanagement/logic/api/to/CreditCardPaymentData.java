package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.CreditCardType;

/**
 * This class acts as a POJO.
 *
 */
public class CreditCardPaymentData extends PaymentData {

  private static final long serialVersionUID = 1L;

  private String creditCardNumber;

  private String creditCardCode;

  private String creditCardOwnerName;

  private String creditCardOwnerSurname;

  private String creditCardEndOfValidity;

  private CreditCardType creditCardType;

  /**
   * Returns the field 'creditCardNumber'.
   * 
   * @return Value of creditCardNumber
   */
  public String getCreditCardNumber() {

    return this.creditCardNumber;
  }

  /**
   * Sets the field 'creditCardNumber'.
   * 
   * @param creditCardNumber New value for creditCardNumber
   */
  public void setCreditCardNumber(String creditCardNumber) {

    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Returns the field 'creditCardCode'.
   * 
   * @return Value of creditCardCode
   */
  public String getCreditCardCode() {

    return this.creditCardCode;
  }

  /**
   * Sets the field 'creditCardCode'.
   * 
   * @param creditCardCode New value for creditCardCode
   */
  public void setCreditCardCode(String creditCardCode) {

    this.creditCardCode = creditCardCode;
  }

  /**
   * Returns the field 'creditCardOwnerName'.
   * 
   * @return Value of creditCardOwnerName
   */
  public String getCreditCardOwnerName() {

    return this.creditCardOwnerName;
  }

  /**
   * Sets the field 'creditCardOwnerName'.
   * 
   * @param creditCardOwnerName New value for creditCardOwnerName
   */
  public void setCreditCardOwnerName(String creditCardOwnerName) {

    this.creditCardOwnerName = creditCardOwnerName;
  }

  /**
   * Returns the field 'creditCardOwnerSurname'.
   * 
   * @return Value of creditCardOwnerSurname
   */
  public String getCreditCardOwnerSurname() {

    return this.creditCardOwnerSurname;
  }

  /**
   * Sets the field 'creditCardOwnerSurname'.
   * 
   * @param creditCardOwnerSurname New value for creditCardOwnerSurname
   */
  public void setCreditCardOwnerSurname(String creditCardOwnerSurname) {

    this.creditCardOwnerSurname = creditCardOwnerSurname;
  }

  /**
   * Returns the field 'creditCardEndOfValidity'.
   * 
   * @return Value of creditCardEndOfValidity
   */
  public String getCreditCardEndOfValidity() {

    return this.creditCardEndOfValidity;
  }

  /**
   * Sets the field 'creditCardEndOfValidity'.
   * 
   * @param creditCardEndOfValidity New value for creditCardEndOfValidity
   */
  public void setCreditCardEndOfValidity(String creditCardEndOfValidity) {

    this.creditCardEndOfValidity = creditCardEndOfValidity;
  }

  /**
   * Returns the field 'creditCardType'.
   * 
   * @return Value of creditCardType
   */
  public CreditCardType getCreditCardType() {

    return this.creditCardType;
  }

  /**
   * Sets the field 'creditCardType'.
   * 
   * @param creditCardType New value for creditCardType
   */
  public void setCreditCardType(CreditCardType creditCardType) {

    this.creditCardType = creditCardType;
  }

}
