package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

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
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.rowset.serial.SerialBlob;
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

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains methods for REST calls. Some URI structures may seem depricated, but in fact are not. See the
 * correspondent comments on top.
 *
 * @author agreul
 */
@Path("/offermanagement/v1")
@Named("OffermanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class OffermanagementRestServiceImpl {

  private Offermanagement offermanagement;

  /**
   * @param offerManagement the offerManagement to be set
   */
  @Inject
  public void setOffermanagement(Offermanagement offerManagement) {

    this.offermanagement = offerManagement;
  }

  /**
   * Delegates to {@link Offermanagement#findOffer}.
   *
   * @param id the ID of the {@link OfferEto}
   * @return the {@link OfferEto}
   */
  @GET
  @Path("/offer/{id}")
  public OfferEto getOffer(@PathParam("id") Long id) {

    return this.offermanagement.findOffer(id);
  }

  /**
   * Delegates to {@link Offermanagement#saveOffer}.
   *
   * @param offer the {@link OfferEto} to save
   *
   * @return the saved {@link OfferEto}
   */
  @POST
  @Path("/offer/")
  public OfferEto saveOffer(OfferEto offer) {

    return this.offermanagement.saveOffer(offer);
  }

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
  public OfferEto updateOffer(OfferEto offer) {

    return this.offermanagement.saveOffer(offer);
  }

  /**
   * Delegates to {@link Offermanagement#findAllOffers}.
   *
   * @return all {@link OfferEto}s as list
   */
  @GET
  @Path("/offer/")
  @Deprecated
  public List<OfferEto> getAllOffers() {

    return this.offermanagement.findAllOffers();
  }

  /**
   * Delegates to {@link Offermanagement#findAllProducts}.
   *
   * @return all {@link ProductEto}s as list
   */
  @GET
  @Path("/product/")
  @Deprecated
  public List<ProductEto> getAllProducts() {

    return this.offermanagement.findAllProducts();
  }

  /**
   * Delegates to {@link Offermanagement#saveProduct}.
   *
   * @param product the product to save
   * @return the saved product
   */
  @POST
  @Path("/product/")
  public ProductEto saveProduct(ProductEto product) {

    return this.offermanagement.saveProduct(product);
  }

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
  public List<MealEto> getAllMeals() {

    return this.offermanagement.findAllMeals();
  }

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
  public List<DrinkEto> getAllDrinks() {

    return this.offermanagement.findAllDrinks();
  }

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
  public List<SideDishEto> getAllSideDishes() {

    return this.offermanagement.findAllSideDishes();
  }

  /**
   * Delegates to {@link Offermanagement#deleteOffer}.
   *
   * @param id ID of the {@link OfferEto} to delete
   */
  @DELETE
  @Path("/offer/{id}")
  public void deleteOffer(@PathParam("id") Long id) {

    this.offermanagement.deleteOffer(id);
  }

  /**
   * Delegates to {@link Offermanagement#findProductByRevision}.
   *
   * @param id ID of the {@link ProductEto}
   * @param revision revision of the {@link ProductEto}
   * @return the {@link ProductEto}
   */
  @GET
  @Path("/product/{id}/{revision}")
  public ProductEto findProductByRevision(@PathParam("id") Long id, @PathParam("revision") Long revision) {

    if (revision != null) {
      return this.offermanagement.findProductByRevision(id, revision);
    } else {
      return this.offermanagement.findProduct(id);
    }
  }

  /**
   * Delegates to {@link Offermanagement#findProduct}.
   *
   * @param id ID of the {@link ProductEto}
   * @return the {@link ProductEto}
   */
  @GET
  @Path("/product/{id}")
  public ProductEto findProduct(@PathParam("id") Long id) {

    return this.offermanagement.findProduct(id);
  }

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
  public void updateProduct(ProductEto product) {

    this.offermanagement.saveProduct(product);
  }

  /**
   * Delegates to {@link Offermanagement#isProductInUseByOffer}.
   *
   * @param id ID of the {@link ProductEto}
   * @return true, if there are no offers, that use the given ProductEto. false otherwise.
   */
  @GET
  @Path("/product/{id}/inuse")
  public boolean isProductInUseByOffer(@PathParam("id") Long id) {

    return this.offermanagement.isProductInUseByOffer(findProduct(id));
  }

  /**
   * Delegates to {@link Offermanagement#deleteProduct}.
   *
   * @param id ID of the ProductEto to delete
   */
  @DELETE
  @Path("/product/{id}")
  public void deleteProduct(@PathParam("id") Long id) {

    this.offermanagement.deleteProduct(id);
  }

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
  public List<OfferEto> getFilteredOffers(OfferFilter offerFilter, @PathParam("sortBy") OfferSortBy sortBy) {

    return this.offermanagement.findOffersFiltered(offerFilter, sortBy);
  }

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
  public List<ProductEto> getFilteredProducts(ProductFilter productFilter, @PathParam("sortBy") ProductSortBy sortBy) {

    return this.offermanagement.findProductsFiltered(productFilter, sortBy);
  }

  @SuppressWarnings("javadoc")
  @Consumes("multipart/mixed")
  @POST
  @Path("/product/{id}/picture")
  public void updateProductPicture(@PathParam("id") Long productId,
      @Multipart(value = "binaryObjectEto", type = MediaType.APPLICATION_JSON) BinaryObjectEto binaryObjectEto,
      @Multipart(value = "blob", type = MediaType.APPLICATION_OCTET_STREAM) InputStream picture)
      throws SerialException, SQLException, IOException {

    Blob blob = new SerialBlob(IOUtils.readBytesFromStream(picture));
    this.offermanagement.updateProductPicture(productId, blob, binaryObjectEto);

  }

  @SuppressWarnings("javadoc")
  @Produces("multipart/mixed")
  @GET
  @Path("/product/{id}/picture")
  public MultipartBody getProductPicture(@PathParam("id") long productId) throws SQLException, IOException {

    Blob blob = this.offermanagement.findProductPictureBlob(productId);
    // REVIEW arturk88 (hohwille) we need to find another way to stream the blob without loading into heap.
    // https://github.com/oasp/oasp4j-sample/pull/45
    byte[] data = IOUtils.readBytesFromStream(blob.getBinaryStream());

    List<Attachment> atts = new LinkedList<>();
    atts.add(new Attachment("binaryObjectEto", MediaType.APPLICATION_JSON, this.offermanagement
        .findProductPicture(productId)));
    atts.add(new Attachment("blob", MediaType.APPLICATION_OCTET_STREAM, new ByteArrayInputStream(data)));
    return new MultipartBody(atts, true);

  }

  @SuppressWarnings("javadoc")
  @DELETE
  @Path("/product/{id}/picture")
  public void deleteProductPicture(long productId) {

    this.offermanagement.deleteProductPicture(productId);
  }

  /**
   * Delegates to {@link UcFindOffer#findOfferEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding offers.
   * @return the {@link PaginatedListTo list} of matching {@link OfferEto}s.
   */
  @Path("/offer/search")
  @POST
  public PaginatedListTo<OfferEto> findOfferEtosByPost(OfferSearchCriteriaTo searchCriteriaTo) {

    return this.offermanagement.findOfferEtos(searchCriteriaTo);
  }

  /**
   * Delegates to {@link UcFindProduct#findProductEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding products.
   * @return the {@link PaginatedListTo list} of matching {@link ProductEto}s.
   */
  @Path("/product/search")
  @POST
  public PaginatedListTo<ProductEto> findProductEtosByPost(ProductSearchCriteriaTo searchCriteriaTo) {

    return this.offermanagement.findProductEtos(searchCriteriaTo);
  }

}
