package io.oasp.gastronomy.restaurant.general.service.impl.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicHeaderValueParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.oasp.gastronomy.restaurant.general.common.api.ThreadLocals;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalHeaderValueException;
import io.oasp.module.basic.csv.CsvFormat;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * Cette classe est responsable de la conversion entre Eto et ligne CSV. Elle est implicite dans la couche service. Pour
 * obtenir un retour CSV de l'API le client doit fournir le header HTTP
 * <code>{@link ThreadLocals#ACCEPT_COLUMNS Accept-Header}</code>. Pour envoyer du CSV le client doit fournir le header
 * <code>Content-Header</code> indiquant le nom des colonnes (<b>le nom des attributs de l'ETO</b> ou des colonnes CSV
 * si les getters de l'ETO sont annotés par {@link JsonProperty @JsonProperty}) envoyées dans le body séparé par "," ou
 * ";".
 *
 * <p>
 * Diagramme expliquant la requête et la réponse entre le client et le serveur :
 * </p>
 *
 *
 * <pre>
 *         CLIENT                                                                       SERVEUR
 * +-------------------+                                                         +-------------------+
 * |                   |Content-Type: text/csv                                   |                   |
 * | val1;val2         |Content-Header: col1, col2                              | val1;val3         |
 * |                   |                                                         |                   |
 * |                   +-------------------------------------------------------> |                   |
 * |                   |                                                         |                   |
 * |                   |                                                         |                   |
 * |                   |Accept: text/csv               Content-Type: text/csv    |                   |
 * |                   |Accept-Header: col1, col3    Content-Header: col1, col3|                   |
 * |                   |X-Date-Format: dd/MM/yyyy                                |                   |
 * |                   |                                                         |                   |
 * |                   | <-------------------------------------------------------+                   |
 * +-------------------+                                                         +-------------------+
 * </pre>
 *
 * <p>
 * Si le CSV contient déjà un en-tête avec la liste des colonnes CSV le header Content-Header n'est pas obligatoire
 * </p>
 *
 *
 * <pre>
 *         CLIENT                                                                       SERVEUR
 * +-------------------+                                                         +-------------------+
 * |                   |Content-Type: text/csv                                   |                   |
 * | col1;col2         |                                                         | val1;val3         |
 * | val1;val2         |                                                         |                   |
 * |                   +-------------------------------------------------------> |                   |
 * |                   |                                                         |                   |
 * |                   |                                                         |                   |
 * |                   |Accept: text/csv               Content-Type: text/csv    |                   |
 * |                   |Accept-Header: col1, col3    Content-Header: col1, col3|                   |
 * |                   |X-Date-Format: dd/MM/yyyy                                |                   |
 * |                   |                                                         |                   |
 * |                   | <-------------------------------------------------------+                   |
 * +-------------------+                                                         +-------------------+
 * </pre>
 *
 * <p>
 * Description des headers envoyés par le client :
 * </p>
 *
 * <ul>
 * <li><code>Content-Type: text/csv</code> : informe l'API qu'on lui envoie du CSV dans le body de la requête</li>
 * <li><code>Content-Header</code> : listes des colonnes CSV envoyées dans le body dans cet ordre</li>
 * <li><code>Accept: text/csv</code> : demande à l'API de renvoyer du CSV dans le body de sa réponse</li>
 * <li><code>Accept-Header</code> : demande à l'API de renvoyer uniquement certaines colonnes CSV et dans cet ordre</li>
 * <li><code>X-Date-Format</code> : demande à l'API de formater les dates selon le format fourni. Utilise la syntax de
 * {@link SimpleDateFormat}
 * </ul>
 *
 * <p>
 * TODO : dans le cas où le client n'envoie pas de Accept-Header réutiliser les colonnes reçues en entrée ssi les ETO à
 * renvoyer sont de même type que les ETO reçus. Si les ETO renvoyés sont d'un autre type renvoyer par défaut toutes les
 * colonnes possibles.
 * </p>
 *
 * <p>
 * En cas d'erreur <code>com.fasterxml.jackson.core.JsonGenerationException: CSV generator does not support
   * Object values for properties</code> veuillez annoter l'Eto/Cto serialisé avec :
 * </p>
 *
 * <pre>
 * &#64;JsonFilter(CsvProvider.FILTER)
 * </pre>
 *
 * <p>
 * Ce qui permet d'empêcher Jackson de serialiser tous les champs même ceux qui ne sont pas demandés par le client dans
 * son header {@link ThreadLocals#ACCEPT_COLUMNS}
 * </p>
 *
 * @author mlavigne
 * @see <a href="https://jersey.java.net/documentation/latest/message-body-workers.html">reference</a>
 */
@Provider
@Consumes(CsvProvider.MEDIA_TYPE)
@Produces(CsvProvider.MEDIA_TYPE)
@Named
public class CsvProvider implements ContainerRequestFilter, MessageBodyWriter<Object>, MessageBodyReader<Object> {

  /**
   *
   */
  public static final String ACCEPT_QUOTE_CHAR = "Accept-Quote-Char";

  public static final String CONTENT_QUOTE_CHAR = "Content-Quote-Char";

  /**
   *
   */
  public static final String ACCEPT_COLUMN_SEPARATOR_HEADER = "Accept-Column-Separator";

  public static final String CONTENT_COLUMN_SEPARATOR_HEADER = "Content-Column-Separator";

  /** RFC 7111 Content-Type for CSV content */
  // TODO : handle "application/vnd.ms-excel" in another CsvProvider () that extends this one
  public static final String MEDIA_TYPE = "text/csv"; // TODO : rename to MEDIA_TYPE

  /** Séparateur des noms de colonnes dans les headers Columns, Accept-Header et Content-Header */
  public static final String COLUMNS_HEADER_SEPARATOR = ","; // TODO : automaticaly switch depending on Column-Separator

  /** Nom du header "Content-Header" indiquant le noms des colonnes correspondant à la ligne CSV dans le body */
  // TODO : si non spécifié alors on considère que la 1ère ligne sont les headers
  public static final String CONTENT_HEADER = "Content-Header";

  public static final String ACCEPT_HEADER = "Accept-Header";

  public static final String ANSI_ENCODING = "Cp1252";

  // TODO : use application.properties to get default values
  public static final CsvFormat DEFAULT_CSV_FORMAT = new CsvFormat().withNullValue(StringUtils.EMPTY) // On considère la
                                                                                                      // chaîne vide
                                                                                                      // comme null
      .withColumnSeparator(';').withQuoteChar('"').withLineSeparator("\r\n").withoutEndingLineSeparator()
      .withDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(CsvProvider.class);

  @Context
  HttpServletRequest request;

  /**
   * The constructor.
   */
  public CsvProvider() {
    // NOP
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

    return true;
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

    return true;
  }

  @Override
  public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

    // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
    return 0;
  }

  @Override
  public void writeTo(Object eto, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {

    // Format accepté par le client
    final CsvFormat responseFormat = (CsvFormat) this.request.getAttribute("responseFormat");

    // Si le service REST renvoie une chaine non parsée, on renvoie la chaîne telle quelle.
    // Du coup les headers "Columns" ne sont pas utilisés puisqu'on ne parse par le CSV
    if (CharSequence.class.isAssignableFrom(type)) {
      IOUtils.write((CharSequence) eto, entityStream, responseFormat != null ? responseFormat.getCharset() : null);
      return;
    }

    if (PaginatedListTo.class.isAssignableFrom(type)) {
      // On peut transmettre genericType car le E de PaginatedListTo<E> est le même que celui de Iterable<E>
      final List<?> result = ((PaginatedListTo<?>) eto).getResult();
      writeTo(result, result.getClass(), genericType, annotations, mediaType, httpHeaders, entityStream);
      return;
    }

    final String charset = responseFormat.getCharset();
    if (httpHeaders != null) { // null dans les TU
      // On répète au client les colonnes renvoyées dans le body de la réponse dans un header Content-Header
      httpHeaders.add(CONTENT_HEADER, this.request.getHeader(ACCEPT_HEADER));
      if (charset != null) {
        httpHeaders.putSingle(HttpHeaders.CONTENT_TYPE, CsvProvider.MEDIA_TYPE + ";charset=" + charset);
      }
    }

    responseFormat.writeValue(entityStream, eto);
  }

  @Override
  public void filter(ContainerRequestContext req) throws IOException {

    final MediaTypeHeaderElement contentType =
        MediaTypeHeaderElement.parse(HttpHeaders.CONTENT_TYPE, req.getHeaderString(HttpHeaders.CONTENT_TYPE));
    if (contentType != null) {
      if (MEDIA_TYPE.equals(contentType.mediaType)) {
        req.setProperty("requestFormat", getRequestFormat(req, contentType));
      }
    }

    final MediaTypeHeaderElement accept =
        MediaTypeHeaderElement.parse(HttpHeaders.ACCEPT, req.getHeaderString(HttpHeaders.ACCEPT));
    if (accept != null) {
      if (MEDIA_TYPE.equals(accept.mediaType)) {
        req.setProperty("responseFormat", getResponseFormat(req, accept));
      }
    }
  }

  public static final class MediaTypeHeaderValueParser extends BasicHeaderValueParser {

    @Override
    protected HeaderElement createHeaderElement(String name, String value, NameValuePair[] params) {

      return new MediaTypeHeaderElement(name, value, params);
    }
  }

  public static final class MediaTypeHeaderElement extends BasicHeaderElement {

    /**
     * The constructor.
     *
     * @param name
     * @param value
     * @param parameters
     */
    public MediaTypeHeaderElement(String name, String value, NameValuePair[] parameters) {
      super(name, value, parameters);

      this.mediaType = getName();
      final NameValuePair charset = getParameterByName("charset");
      if (charset != null) {
        this.charset = charset.getValue();
      }
      final NameValuePair headerParam = getParameterByName("header");
      if (headerParam != null) {
        final String headerParamValue = headerParam.getValue();
        if ("present".equals(headerParamValue)) {
          this.headerPresent = true;
        } else if ("absent".equals(headerParamValue)) {
          this.headerPresent = false;
        } else {
          throw new IllegalHeaderValueException(getName(), getValue());
        }
      }
    }

    /** @see MediaType */
    private String mediaType;

    private String charset;

    /** default is <code>false</code> */
    private boolean headerPresent = false;

    /**
     *
     * @param headerName "Content-Type" or "Accept" // FIXME not used
     * @param headerValue Content-Type/Accept header value
     * @return
     *
     * @see HttpHeaders#CONTENT_TYPE
     * @see HttpHeaders#ACCEPT
     */
    public static final MediaTypeHeaderElement parse(String headerName, String headerValue) {

      if (StringUtils.isNotBlank(headerValue)) {
        final HeaderElement element =
            MediaTypeHeaderValueParser.parseHeaderElement(headerValue, new MediaTypeHeaderValueParser());
        return new MediaTypeHeaderElement(element.getName(), element.getValue(), element.getParameters());
      }
      return null;
    }
  }

  /**
   * @param req
   * @param mediaType
   * @return
   */
  private CsvFormat getRequestFormat(ContainerRequestContext req, MediaTypeHeaderElement mediaType) {

    // Content-Column-Separator
    final String columnSeparatorHeader = req.getHeaderString(CONTENT_COLUMN_SEPARATOR_HEADER);

    // Content-Quote-Char
    final String quoteChar = req.getHeaderString(CONTENT_QUOTE_CHAR);

    // Content-Header
    final String columnsHeader = req.getHeaderString(CONTENT_HEADER);

    // Content-Charset header does not exist ; it is a parameter in the mediaType header
    final String charsetHeader = null;

    return getFormat(columnSeparatorHeader, columnsHeader, quoteChar, charsetHeader, mediaType);
  }

  /**
   * @param req
   * @param mediaType
   * @return
   */
  private CsvFormat getResponseFormat(ContainerRequestContext req, MediaTypeHeaderElement mediaType) {

    // Accept-Column-Separator
    final String columnSeparatorHeader = req.getHeaderString(ACCEPT_COLUMN_SEPARATOR_HEADER);

    // Accept-Quote-Char
    final String quoteChar = req.getHeaderString(ACCEPT_QUOTE_CHAR);

    // Accept-Header
    final String columnsHeader = req.getHeaderString(ACCEPT_HEADER);

    // Accept-Charset
    final String charsetHeader = req.getHeaderString(HttpHeaders.ACCEPT_CHARSET);

    return getFormat(columnSeparatorHeader, columnsHeader, quoteChar, charsetHeader, mediaType);
  }

  /**
   * @param columnSeparatorHeader
   * @param columnsHeader
   * @param quoteChar
   * @param charsetHeader
   * @param mediaType
   * @return
   */
  private CsvFormat getFormat(String columnSeparatorHeader, String columnsHeader, String quoteChar,
      String charsetHeader, MediaTypeHeaderElement mediaType) {

    final Character columnSeparator = StringUtils.isNotBlank(columnSeparatorHeader) ? columnSeparatorHeader.charAt(0)
        : CsvFormat.DEFAULT_COLUMN_SEPARATOR;

    CsvFormat format = DEFAULT_CSV_FORMAT;

    // *-Column-Separator
    if (columnSeparator != null) {
      format = format.withColumnSeparator(columnSeparator);

      // *-Header
      if (StringUtils.isNotBlank(columnsHeader)) {
        format = format.withColumns(CsvFormat.getColumns(columnsHeader, columnSeparator));
      }
    }

    // explicit header param in MediaType header or implicit because columns are unknown in HTTP Headers
    if (format.getColumns() == null || mediaType != null && mediaType.headerPresent) {
      format = format.withHeader();
    }

    // *-Quote-Char
    if (StringUtils.isNotBlank(quoteChar)) {
      format = format.withQuoteChar(quoteChar.charAt(0));
    }

    // charset
    final String charset;
    if (StringUtils.isNotBlank(charsetHeader)) {
      charset = charsetHeader;
    } else if (mediaType != null) {
      charset = mediaType.charset;
    } else {
      charset = null;
    }
    if (charset != null) {
      format = format.withCharset(charset);
    }

    return format;
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
      throws IOException, WebApplicationException {

    // Si le service REST s'attend à recevoir une chaine non parsée, on lui renvoie la chaîne telle quelle.
    // Du coup les headers "Columns" ne sont pas utilisés puisqu'on ne parse par le CSV
    if (CharSequence.class.isAssignableFrom(type)) {
      return IOUtils.toString(entityStream);
    }

    // Format utilisé par le client
    final CsvFormat requestFormat = (CsvFormat) this.request.getAttribute("requestFormat");
    return requestFormat.readValue(entityStream, type);
  }

}
