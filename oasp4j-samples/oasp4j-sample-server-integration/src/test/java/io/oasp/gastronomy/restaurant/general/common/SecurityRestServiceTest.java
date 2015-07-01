package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.gastronomy.restaurant.general.common.api.to.UserDetailsClientTo;
import io.oasp.gastronomy.restaurant.test.general.AppProperties;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;
import io.oasp.gastronomy.restaurant.test.general.webclient.WebClientWrapper;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test security features.
 *
 * @author jmetzler
 */
public class SecurityRestServiceTest {

  /**
   * Login and check if current user is the one logged in.
   */
  @Test
  public void loginTest() {

    String login = "chief";
    WebClientWrapper user = new WebClientWrapper(login);

    ResponseData<UserDetailsClientTo> response =
        user.get(AppProperties.SERVER_URL + "/services/rest/security/v1/currentuser/", UserDetailsClientTo.class);

    Assert.assertEquals(login, response.getResponseObject().getName());
  }
}
