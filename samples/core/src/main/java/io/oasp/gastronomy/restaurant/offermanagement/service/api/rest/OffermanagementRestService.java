package io.oasp.gastronomy.restaurant.offermanagement.service.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import io.oasp.gastronomy.restaurant.general.common.api.RestService;
import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest.OffermanagementRestServiceImpl;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 *
 * This class contains methods for REST calls. Some URI structures may seem deprecated, but in fact are not. See the
 * correspondent comments on top.
 *
 */

@Path("/offermanagement/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OffermanagementRestService extends RestService {

  /**
   * Delegates to {@link Offermanagement#findOffer}.
   *
   * @param id the ID of the {@link OfferEto}
   * @return the {@link OfferEto}
   */
  @GET
  @Path("/offer/{id}")
  OfferEto getOffer(@PathParam("id") long id);

  /**
   * Delegates to {@link Offermanagement#saveOffer}.
   *
   * @param offer the {@link OfferEto} to save
   *
   * @return the saved {@link OfferEto}
   */
  @POST
  @Path("/offer/")
  public OfferEto saveOffer(OfferEto offer);

  // although id in path is redundant, this structure is intentionally chosen
  // for further reasons behind this decision see one of the other ***ManagementRestServiceImpl
  /**
   * Delegates to {@link Offermanagement#saveOffer}.
   *
   * @param offer the {@link OfferEto} to be updated
   *
   * @return the updated {@link OfferEto}
   */
  @PUT
  @Path("/offer/{id}")
  @Deprecated
  OfferEto updateOffer(OfferEto offer);

  /**
   * Delegates to {@link Offermanagement#findAllOffers}.
   *
   * @return all {@link OfferEto}s as list
   */
  @GET
  @Path("/offer/")
  @Deprecated
  public List<OfferEto> getAllOffers();

  /**
   * Delegates to {@link Offermanagement#findAllProducts}.
   *
   * @return all {@link ProductEto}s as list
   */
  @GET
  @Path("/product/")
  @Deprecated
  List<ProductEto> getAllProducts();

  /**
   * Delegates to {@link Offermanagement#saveProduct}.
   *
   * @param product the product to save
   * @return the saved product
   */
  @POST
  @Path("/product/")
  ProductEto saveProduct(ProductEto product);

  /**
   * Delegates to {@link Offermanagement#findAllMeals}.
   *
   * @deprecated use {@link OffermanagementRestServiceImpl#findProductEtosByPost(ProductSearchCriteriaTo)} with
   *             fetchMeals=true
   * @return all {@link MealEto}s as list
   */
  @GET
  @Path("/product/meal/")
  @Deprecated
  List<MealEto> getAllMeals();

  /**
   * Delegates to {@link Offermanagement#findAllDrinks}.
   *
   * @deprecated use {@link OffermanagementRestServiceImpl#findProductEtosByPost(ProductSearchCriteriaTo)} with
   *             fetchDrinks=true
   * @return all {@link DrinkEto}s as list
   */
  @GET
  @Path("/product/drink/")
  @Deprecated
  List<DrinkEto> getAllDrinks();

  /**
   * Delegates to {@link Offermanagement#findAllSideDishes}.
   *
   * @deprecated use {@link OffermanagementRestServiceImpl#findProductEtosByPost(ProductSearchCriteriaTo)} with
   *             fetchSideDishes=true
   * @return all {@link SideDishEto}s as list
   */
  @GET
  @Path("/product/side/")
  @Deprecated
  List<SideDishEto> getAllSideDishes();

  /**
   * Delegates to {@link Offermanagement#deleteOffer}.
   *
   * @param id ID of the {@link OfferEto} to delete
   */
  @DELETE
  @Path("/offer/{id}")
  void deleteOffer(@PathParam("id") long id);

  /**
   * Delegates to {@link Offermanagement#findProductByRevision}.
   *
   * @param id ID of the {@link ProductEto}
   * @param revision revision of the {@link ProductEto}
   * @return the {@link ProductEto}
   */
  @GET
  @Path("/product/{id}/{revision}")
  ProductEto findProductByRevision(@PathParam("id") long id, @PathParam("revision") Long revision);

  /**
   * Delegates to {@link Offermanagement#findProduct}.
   *
   * @param id ID of the {@link ProductEto}
   * @return the {@link ProductEto}
   */
  @GET
  @Path("/product/{id}")
  ProductEto findProduct(@PathParam("id") long id);

  // although id in path is redundant, this structure is intentionally chosen
  // for further reasons behind this decision see one of the other
  // *ManagementRestServiceImpl
  /**
   * Delegates to {@link Offermanagement#saveProduct}.
   *
   * @param product the {@link ProductEto} to be updated
   */
  @PUT
  @Path("/product/{id}")
  @Deprecated
  void updateProduct(ProductEto product);

  /**
   * Delegates to {@link Offermanagement#isProductInUseByOffer}.
   *
   * @param id ID of the {@link ProductEto}
   * @return true, if there are no offers, that use the given ProductEto. false otherwise.
   */
  @GET
  @Path("/product/{id}/inuse")
  boolean isProductInUseByOffer(@PathParam("id") long id);

  /**
   * Delegates to {@link Offermanagement#deleteProduct}.
   *
   * @param id ID of the ProductEto to delete
   */
  @DELETE
  @Path("/product/{id}")
  void deleteProduct(@PathParam("id") long id);

  /**
   * Delegates to {@link Offermanagement#findOffersFiltered}.
   *
   * @param offerFilter the offers filter criteria
   * @param sortBy sorting specification
   * @return list with all {@link OfferEto}s that match the {@link OfferFilter} criteria
   */
  @GET
  @Path("/sortby/{sortBy}")
  @Deprecated
  List<OfferEto> getFilteredOffers(OfferFilter offerFilter, @PathParam("sortBy") OfferSortBy sortBy);

  /**
   * Delegates to {@link Offermanagement#findProductsFiltered}.
   *
   * @param productFilter filter specification
   * @param sortBy sorting specification
   * @return list with all {@link ProductEto}s that match the {@link ProductFilter} criteria
   */
  @GET
  @Path("/product/sortby/{sortBy}")
  @Deprecated
  List<ProductEto> getFilteredProducts(ProductFilter productFilter, @PathParam("sortBy") ProductSortBy sortBy);

  @SuppressWarnings("javadoc")
  @Consumes("multipart/mixed")
  @POST
  @Path("/product/{id}/picture")
  public void updateProductPicture(@PathParam("id") long productId,
      @Multipart(value = "binaryObjectEto", type = MediaType.APPLICATION_JSON) BinaryObjectEto binaryObjectEto,
      @Multipart(value = "blob", type = MediaType.APPLICATION_OCTET_STREAM) InputStream picture)
          throws SerialException, SQLException, IOException;

  @SuppressWarnings("javadoc")
  @Produces("multipart/mixed")
  @GET
  @Path("/product/{id}/picture")
  public MultipartBody getProductPicture(@PathParam("id") long productId) throws SQLException, IOException;

  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @GET
  @Path("/product/{id}/rawpicture")
  @Deprecated
  public Response getProductRawPicture(@PathParam("id") long productId) throws SQLException, IOException;

  @SuppressWarnings("javadoc")
  @DELETE
  @Path("/product/{id}/picture")
  public void deleteProductPicture(long productId);

  /**
   * Delegates to {@link Offermanagement#findOfferEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding offers.
   * @return the {@link PaginatedListTo list} of matching {@link OfferEto}s.
   */
  @Path("/offer/search")
  @POST
  public PaginatedListTo<OfferEto> findOfferEtosByPost(OfferSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link Offermanagement#findProductEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding products.
   * @return the {@link PaginatedListTo list} of matching {@link ProductEto}s.
   */
  @Path("/product/search")
  @POST
  public PaginatedListTo<ProductEto> findProductEtosByPost(ProductSearchCriteriaTo searchCriteriaTo);
}
