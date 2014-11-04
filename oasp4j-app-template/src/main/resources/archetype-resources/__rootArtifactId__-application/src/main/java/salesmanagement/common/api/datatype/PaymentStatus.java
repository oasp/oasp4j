#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.common.api.datatype;

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
     * @return <code>true</code>, if the {@link PaymentStatus} equals {@link PaymentStatus${symbol_pound}SUCCESS}.
     *         <code>false</code> otherwise.
     */
    public boolean isSuccessful() {
        return this == SUCCESS;
    }

    /**
     * 
     * @return <code>true</code>, if the {@link PaymentStatus} equals {@link PaymentStatus${symbol_pound}INPUT_DATA_ERROR}
     *         or {@link PaymentStatus${symbol_pound}TIME_OUT_ERROR}. <code>false</code> otherwise.
     */
    public boolean isUnsuccessful() {
        return (this == INPUT_DATA_ERROR || this == TIME_OUT_ERROR);
    }
}
