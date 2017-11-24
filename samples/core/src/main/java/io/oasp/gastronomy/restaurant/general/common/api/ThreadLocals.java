package io.oasp.gastronomy.restaurant.general.common.api;

import java.text.DateFormat;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.service.impl.rest.CsvProvider;

/**
 * Variables pouvant être positionnées par n'importe quelle classe. La valeur stockée sera disponible uniquement pour le
 * thread courant. Ce mécanisme permet notamment de transmettre des id de la couche service à la couche data_access.
 *
 * @author mlavigne
 */
public final class ThreadLocals {

  /**
   * Nom des colonnes qu'on souhaite retrouver en retour de l'API (dans le cas d'un retour CSV par exemple). Exemple
   * HTTP :
   *
   * <pre>
   * Columns: nomDsp,codeCommuneNra,typePf
   * </pre>
   *
   * @see CsvProvider
   */
  public static final ThreadLocal<List<String>> ACCEPT_COLUMNS = new ThreadLocal<>();

  /**
   * @see CsvProvider
   */
  public static final ThreadLocal<String> ACCEPT_CHARSET = new ThreadLocal<>();

  /**
   * @see CsvProvider
   */
  public static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<>();

  private ThreadLocals() {
  }

}
