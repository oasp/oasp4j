package io.oasp.gastronomy.restaurant.offermanagement.logic.api;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;

/**
 * Interface for OfferManagement.
 * 
 * @author loverbec
 */
public interface Offermanagement extends UcFindOffer, UcManageOffer, UcFindProduct, UcManageProduct {

}
