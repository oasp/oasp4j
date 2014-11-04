#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api;

import ${package}.offermanagement.logic.api.usecase.UcFindOffer;
import ${package}.offermanagement.logic.api.usecase.UcFindProduct;
import ${package}.offermanagement.logic.api.usecase.UcManageOffer;
import ${package}.offermanagement.logic.api.usecase.UcManageProduct;

/**
 * Interface for OfferManagement.
 * 
 * @author loverbec
 */
public interface Offermanagement extends UcFindOffer, UcManageOffer, UcFindProduct, UcManageProduct {

}
