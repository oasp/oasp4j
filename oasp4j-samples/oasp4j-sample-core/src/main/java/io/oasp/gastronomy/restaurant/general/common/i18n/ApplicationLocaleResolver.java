package io.oasp.gastronomy.restaurant.general.common.i18n;

import io.oasp.gastronomy.restaurant.general.common.api.security.UserData;
import io.oasp.module.security.common.impl.rest.ApplicationWebAuthenticationDetails;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * This class is responsible to load the different i18n bundles and read messages from user locale bundle.
 *
 */
public class ApplicationLocaleResolver {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationLocaleResolver.class);

  private static final String MESSAGE_NOT_FOUND_ERROR = "MESSAGE NOT FOUND";

  private Locale defaultLanguage;

  private Map<String, Properties> i18nBundles;

  /**
   * The constructor.
   */
  public ApplicationLocaleResolver() {

    this.defaultLanguage = new Locale("en", "EN");
    this.i18nBundles = new HashMap<>();
  }

  /**
   * Get the i18n message from code
   *
   * @param messageCode Key for the message
   * @return Return the message or 'MESSAGE NOT FOUND'
   */
  public String getMessage(String messageCode) {

    Locale language = getUserLocale();
    Properties languageBundle = loadMessageBundle(language.toString());

    if (languageBundle == null) {
      LOG.debug("Not found message bundle for language: " + language);
      return MESSAGE_NOT_FOUND_ERROR;

    } else {

      String msg = languageBundle.getProperty(messageCode);

      if (msg == null) {
        LOG.debug("Not found message for code: " + messageCode);
        return MESSAGE_NOT_FOUND_ERROR;
      }

      return msg;
    }

  }

  /**
   * Get the i18n message from code, with specific arguments
   *
   * @param messageCode Key for the message
   * @param arguments Arguments for the message
   * @return Return the message or 'MESSAGE NOT FOUND'
   */
  public String getMessage(String messageCode, Object... arguments) {

    return MessageFormat.format(getMessage(messageCode), arguments);
  }

  /**
   * @param defaultLanguage new value of defaultLanguage.
   */
  public void setDefaultLanguage(Locale defaultLanguage) {

    this.defaultLanguage = defaultLanguage;
  }

  /**
   * Get language from user
   *
   * @return locale User language
   */
  private Locale getUserLocale() {

    Locale userLanguage = null;

    // Check if is an anonymous user to extract language from authentication details
    if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
      AnonymousAuthenticationToken userInfo =
          (AnonymousAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

      if (userInfo.getDetails() != null) {
        ApplicationWebAuthenticationDetails details = (ApplicationWebAuthenticationDetails) userInfo.getDetails();
        userLanguage = details.getLanguage();
      }
    }
    // if is a logged user we get language information from user data
    else {
      userLanguage = UserData.get().toClientTo().getLanguage();
    }

    // if in this point is null user language, we use defaultLanguage
    if (userLanguage == null) {
      userLanguage = this.defaultLanguage;
    }

    return userLanguage;

  }

  /**
   * Load a message properties file for a user language
   *
   * @param language Language to load
   */
  private Properties loadMessageBundle(String language) {

    if (!this.i18nBundles.containsKey(language)) {
      addBundle(language);
    }

    return this.i18nBundles.get(language);

  }

  /**
   * Load an i18n bundle to map
   *
   * @param language
   */
  private void addBundle(String language) {

    try {

      if (!this.i18nBundles.containsKey(language)) {

        Properties bundle = new Properties();
        bundle.load(new ClassPathResource(buildI18nFieldName(language)).getInputStream());

        this.i18nBundles.put(language, bundle);
      }
    } catch (IOException e1) {
      LOG.debug("Not found i18n bundle for language: " + language);
    }
  }

  /**
   * Build the name of i18n file
   *
   * @param language
   * @return Name of i18n file
   */
  private String buildI18nFieldName(String language) {

    StringBuilder filename = new StringBuilder("config/app/i18n/ApplicationMessages");

    filename.append("_" + language);
    filename.append(".properties");

    return filename.toString();
  }

}
