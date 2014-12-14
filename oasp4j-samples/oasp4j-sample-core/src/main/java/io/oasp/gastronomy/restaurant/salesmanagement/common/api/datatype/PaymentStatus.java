package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * 
 * @author etomety
 */
public enum PaymentStatus {

    /**
     * This status describes a successful payment transaction.
     */
    SUCCESS,

    /**
     * This status describes a unsuccessful payment transaction. The input data is not correct.
     */
    INPUT_DATA_ERROR,

    /**
     * This status describes a unsuccessful payment transaction. The connection timed out.
     */
    TIME_OUT_ERROR;

    /**
     * @return <code>true</code>, if the {@link PaymentStatus} equals {@link PaymentStatus#SUCCESS}.
     *         <code>false</code> otherwise.
     */
    public boolean isSuccessful() {
        return this == SUCCESS;
    }

    /**
     * 
     * @return <code>true</code>, if the {@link PaymentStatus} equals {@link PaymentStatus#INPUT_DATA_ERROR}
     *         or {@link PaymentStatus#TIME_OUT_ERROR}. <code>false</code> otherwise.
     */
    public boolean isUnsuccessful() {
        return (this == INPUT_DATA_ERROR || this == TIME_OUT_ERROR);
    }
}
