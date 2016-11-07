package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * Enum with the types of a credit card.
 *
 */
public enum CreditCardType {

  /** This represents the VISA card. */
  VISA,

  /** This represents the American Express card. */
  AMEX,

  /** This represents the Mastercard. */
  MASTERCARD;

  /**
   * @return {@code true}, if the {@link CreditCardType} equals {@link CreditCardType#VISA}. {@code false}
   *         otherwise.
   */
  public boolean isVisa() {

    return this == VISA;
  }

  /**
   * @return {@code true}, if the {@link CreditCardType} equals {@link CreditCardType#AMEX}. {@code false}
   *         otherwise.
   */
  public boolean isAmex() {

    return this == AMEX;
  }

  /**
   * @return {@code true}, if the {@link CreditCardType} equals {@link CreditCardType#MASTERCARD}.
   *         {@code false} otherwise.
   */
  public boolean isMastercard() {

    return this == MASTERCARD;
  }
}
