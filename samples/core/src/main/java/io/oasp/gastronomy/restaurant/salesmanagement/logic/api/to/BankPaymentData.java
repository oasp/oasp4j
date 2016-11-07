package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

/**
 * This class acts as a POJO.
 *
 */
public class BankPaymentData extends PaymentData {

  private static final long serialVersionUID = 1L;

  private String accountOwnerName;

  private Long accountNumber;

  private Long bankCode;

  private String bankName;

  /**
   * Returns the field 'accountOwnerName'.
   * 
   * @return Value of accountOwnerName
   */
  public String getAccountOwnerName() {

    return this.accountOwnerName;
  }

  /**
   * Sets the field 'accountOwnerName'.
   * 
   * @param accountOwnerName New value for accountOwnerName
   */
  public void setAccountOwnerName(String accountOwnerName) {

    this.accountOwnerName = accountOwnerName;
  }

  /**
   * Returns the field 'accountNumber'.
   * 
   * @return Value of accountNumber
   */
  public Long getAccountNumber() {

    return this.accountNumber;
  }

  /**
   * Sets the field 'accountNumber'.
   * 
   * @param accountNumber New value for accountNumber
   */
  public void setAccountNumber(Long accountNumber) {

    this.accountNumber = accountNumber;
  }

  /**
   * Returns the field 'bankCode'.
   * 
   * @return Value of bankCode
   */
  public Long getBankCode() {

    return this.bankCode;
  }

  /**
   * Sets the field 'bankCode'.
   * 
   * @param bankCode New value for bankCode
   */
  public void setBankCode(Long bankCode) {

    this.bankCode = bankCode;
  }

  /**
   * Returns the field 'bankName'.
   * 
   * @return Value of bankName
   */
  public String getBankName() {

    return this.bankName;
  }

  /**
   * Sets the field 'bankName'.
   * 
   * @param bankName New value for bankName
   */
  public void setBankName(String bankName) {

    this.bankName = bankName;
  }

}
