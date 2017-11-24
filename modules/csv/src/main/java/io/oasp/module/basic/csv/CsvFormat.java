package io.oasp.module.basic.csv;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

/**
 * Immutable object
 *
 * @author MLAVIGNE
 * @see <a href="http://www.ietf.org/rfc/rfc4180.txt">RFC-4180</a>
 */
public class CsvFormat {

  public static char DEFAULT_COLUMN_SEPARATOR = ',';

  // TODO : option to skip last line feed
  // check this method com.fasterxml.jackson.dataformat.csv.impl.CsvEncoder.endRow() line 434
  // System.arraycopy(_cfgLineSeparator, 0, _outputBuffer, _outputTail, _cfgLineSeparatorLength);

  /**
   * Valeur à utiliser dans les ETO/CTO annotés {@link JsonFilter}.
   *
   * <pre>
   * &#64;JsonFilter(CsvFormat.FILTER)
   * public class Eto {
   *   // ...
   * }
   * </pre>
   *
   * To avoid : com.fasterxml.jackson.core.JsonGenerationException Unrecognized column '*' (when it happens for
   * undesired columns). Jackson parses every columns and sort them afterward (too late).
   *
   * @see CsvProvider
   */
  public static final String FILTER = "CsvFormat.FILTER";

  private static final PropertyFilter DEFAULT_PROPERTY_FILTER = buildPropertyFilter(null);

  /** Filtre par défaut si le client ne demande pas de colonnes particulières en CSV */
  public static final SimpleFilterProvider SIMPLE_FILTER_PROVIDER =
      new SimpleFilterProvider().addFilter(FILTER, DEFAULT_PROPERTY_FILTER);

  /** key : Colonnes jointes par hashcode */
  private static final Map<Integer, FilterProvider> filterProviders = new HashMap<>();

  /** may be <code>null</code> */
  protected final List<String> columns;

  protected final CsvMapper mapper;

  // should never be publicly accessible because of #withColumns()
  protected final CsvSchema schema;

  /** may be <code>null</code> */
  protected final String charset;

  protected final DateFormat dateFormat;

  @Deprecated // delete when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
  private boolean endingLineSeparator = true;

  /**
   * The constructor.
   */
  public CsvFormat() {
    this(null, buildMapper(null, null), buildSchema(null), null, null, true);
  }

  /**
   * The constructor. columns must be exactly the same through mapper.filter and schema
   */
  // uncomment when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
  // protected CsvFormat(final List<String> columns, final CsvMapper mapper, final CsvSchema schema, final String
  // charset,
  // final DateFormat dateFormat) {
  // Objects.requireNonNull(mapper);
  // Objects.requireNonNull(schema);
  // this.columns = columns;
  // this.mapper = mapper;
  // this.schema = schema;
  // this.charset = charset;
  // this.dateFormat = dateFormat;
  // }

  /**
   * The constructor. columns must be exactly the same through mapper.filter and schema
   *
   * @deprecated delete when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
   */
  @Deprecated // delete when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
  protected CsvFormat(final List<String> columns, final CsvMapper mapper, final CsvSchema schema, final String charset,
      final DateFormat dateFormat, final boolean endingLineSeparator) {
    Objects.requireNonNull(mapper);
    Objects.requireNonNull(schema);
    this.columns = columns;
    this.mapper = mapper;
    this.schema = schema;
    this.charset = charset;
    this.dateFormat = dateFormat;
    this.endingLineSeparator = endingLineSeparator;
  }

  /**
   * The constructor.
   *
   * @param csvFormat
   * @param withNullValue
   */
  // uncomment when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
  // public CsvFormat(CsvFormat base, CsvSchema newSchema) {
  // this(base.columns, base.mapper, newSchema, base.charset, base.dateFormat);
  // }

  /**
   * The constructor.
   *
   * @param csvFormat
   * @param schema2
   * @param b
   */
  @Deprecated // delete when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
  public CsvFormat(CsvFormat base, CsvSchema newSchema, final boolean endingLineSeparator) {
    this(base.columns, base.mapper, newSchema, base.charset, base.dateFormat, endingLineSeparator);
  }

  /**
   * @param columns
   * @return
   */
  public static CsvMapper buildMapper(final List<String> columns, final DateFormat dateFormat) {

    final CsvMapper mapper = new CsvMapper();

    // To avoid : com.fasterxml.jackson.core.JsonGenerationException:
    // "Unrecognized column" or "CSV generator does not support Object values for properties"
    // Solution #1 : use JsonGenerator.Feature.IGNORE_UNKNOWN
    // Solution #2 : use FilterProvider
    if (columns != null) {
      mapper.setFilterProvider(getFilterProvider(columns));
    }
    // mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

    if (dateFormat != null) {
      mapper.setDateFormat(dateFormat);
    }
    // mapper.configure(Feature.STRICT_CHECK_FOR_QUOTING, true);

    return mapper;
  }

  /**
   * @param columns if <code>null</code> create an {@link CsvSchema#emptySchema() emptySchema} with header
   * @return
   */
  public static CsvSchema buildSchema(final List<String> columns) {

    // TODO : test with accented columns

    final CsvSchema schema;
    if (columns != null && !columns.isEmpty()) {
      // Schéma CSV (ordre des 1ères colonnes + reste des colonnes en fonction de l'ETO sauf colonnes annotées
      // JsonIgnore)
      final Builder builder = CsvSchema.builder();
      for (final String column : columns) {
        if (column != null) {
          builder.addColumn(column); // TODO : use CsvSchema.ColumnType type if possible
        }
      }
      schema = builder.build();
    } else {
      schema = CsvSchema.emptySchema();
    }

    return schema;
  }

  /**
   * @param header
   * @return
   */
  public static List<String> getColumns(String header, char columnSeparator) {

    return StringUtils.isNotBlank(header) ? Arrays.asList(header.split("" + columnSeparator)) : null;
  }

  /**
   * @return columns
   */
  public List<String> getColumns() {

    return this.columns;
  }

  /**
   * @return
   */
  public String getCharset() {

    return this.charset;
  }

  /**
   * Filtrage en amont des colonnes pour Jackson. C'est pour cette raison que l'annotation @@Filter
   *
   * @param columns, if <code>null</code> return default filter
   *
   * @see "http://www.baeldung.com/jackson-serialize-field-custom-criteria"
   */
  protected static FilterProvider getFilterProvider(final List<String> columns) {

    final int cacheKey = columns.hashCode();
    if (!filterProviders.containsKey(cacheKey)) {
      final PropertyFilter theFilter = buildPropertyFilter(columns);
      filterProviders.put(cacheKey, new SimpleFilterProvider().addFilter(FILTER, theFilter));
    }

    return filterProviders.get(cacheKey);
  }

  /**
   * @param columns
   * @return
   */
  protected static SimpleBeanPropertyFilter buildPropertyFilter(final List<String> columns) {

    return new SimpleBeanPropertyFilter() {
      @Override
      public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
          throws Exception {

        if (include(writer)) {
          if (columns == null || columns.contains(writer.getName())) {
            writer.serializeAsField(pojo, jgen, provider);
          }
        } else if (!jgen.canOmitFields()) { // since 2.3
          writer.serializeAsOmittedField(pojo, jgen, provider);
        }
      }
    };
  }

  /**
   * Must be the name of the {@link JsonProperty} (if field/getter is annotated). Do not use when you want to read CSV
   * and automatically get columns from the first line
   *
   * @param columns
   * @return
   *
   * @see #withoutColumns()
   */
  public CsvFormat withColumns(final List<String> columns) {

    final CsvMapper mapper = buildMapper(columns, this.dateFormat);
    CsvSchema schema = buildSchema(columns) // TODO : use copy constructor
        .withColumnSeparator(this.schema.getColumnSeparator())
        .withLineSeparator(new String(this.schema.getLineSeparator())).withQuoteChar((char) this.schema.getQuoteChar())
        .withNullValue(this.schema.getNullValueString());
    if (this.schema.usesHeader()) {
      schema = schema.withHeader();
    } else {
      schema = schema.withoutHeader();
    }
    /*
     * FIXME uncomment when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46 if
     * (this.schema.getEndingLineSeparator()) { schema = schema.withEndingLineSeparator(); } else { schema =
     * schema.withoutEndingLineSeparator(); }
     */
    // FIXME keep header
    return new CsvFormat(columns, mapper, schema, this.charset, this.dateFormat, this.endingLineSeparator);
  }

  /**
   * Useful when you want to read CSV and automatically get columns from the first line (it must be the header). The
   * returned format can not be used for writing until you call {@link #withColumns(List)}
   *
   * @return the format to read CSV and detect columns from the header (first line)
   */
  public CsvFormat withoutColumns() {

    return withColumns((List<String>) null).withHeader();
  }

  public CsvFormat withColumns(final String header) {

    return withColumns(getColumns(header, this.schema.getColumnSeparator()));
  }

  /**
   * @param empty
   * @return
   */
  public CsvFormat withNullValue(String nvl) {

    return new CsvFormat(this, this.schema.withNullValue(nvl), this.endingLineSeparator);
  }

  public CsvFormat withHeader() {

    return new CsvFormat(this, this.schema.withHeader(), this.endingLineSeparator);
  }

  /**
   * Shortcut for {@link #withHeader()} and {@link #withColumns(String)}
   *
   * @param header
   * @return
   */
  public CsvFormat withHeader(final String header) {

    return withHeader().withColumns(header);
  }

  public CsvFormat withoutHeader() {

    return new CsvFormat(this, this.schema.withoutHeader(), this.endingLineSeparator);
  }

  public CsvFormat withColumnSeparator(final char sep) {

    return new CsvFormat(this, this.schema.withColumnSeparator(sep), this.endingLineSeparator);
  }

  public char getColumnSeparator() {

    return this.schema.getColumnSeparator();
  }

  public CsvFormat withLineSeparator(final String sep) {

    return new CsvFormat(this, this.schema.withLineSeparator(sep), this.endingLineSeparator);
  }

  public char[] getLineSeparator() {

    return this.schema.getLineSeparator();
  }

  public String getLineSeparatorString() {

    return new String(this.schema.getLineSeparator());
  }

  public CsvFormat withEndingLineSeparator() {

    // FIXME uncomment when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    // return new CsvFormat(this, this.schema.withEndingLineSeparator());
    return new CsvFormat(this, this.schema, true);
  }

  public CsvFormat withoutEndingLineSeparator() {

    // FIXME uncomment when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    // return new CsvFormat(this, this.schema.withoutEndingLineSeparator());
    return new CsvFormat(this, this.schema, false);
  }

  public CsvFormat withQuoteChar(final char c) {

    return new CsvFormat(this, this.schema.withQuoteChar(c), this.endingLineSeparator);
  }

  public CsvFormat withoutQuoteChar() {

    return new CsvFormat(this, this.schema.withoutQuoteChar(), this.endingLineSeparator);
  }

  public CsvFormat withCharset(final String charset) {

    return new CsvFormat(this.columns, this.mapper, this.schema, charset, this.dateFormat, this.endingLineSeparator);
  }

  public CsvFormat withEncoding(final String encoding) {

    return withCharset(encoding);
  }

  /**
   * @param simpleDateFormat
   * @return
   */
  public CsvFormat withDateFormat(DateFormat dateFormat) {

    return new CsvFormat(this.columns, buildMapper(this.columns, dateFormat), this.schema, this.charset, dateFormat,
        this.endingLineSeparator);
  }

  /**
   * @param simpleDateFormat
   * @return
   */
  public CsvFormat withoutDateFormat() {

    return new CsvFormat(this.columns, buildMapper(this.columns, null), this.schema, this.charset, this.dateFormat,
        this.endingLineSeparator);
  }

  /**
   * @param <E>
   * @param type
   * @return
   */
  public <E> ObjectReader readerFor(Class<E> type) { // TODO save readers in CsvFormat fields (cache)

    return this.mapper.readerFor(type).with(this.schema);
  }

  /**
   * @param src
   * @param type
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  public <E> E readValue(InputStream src, Class<E> type) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(type).readValue(encoded);
  }

  /**
   * @param src
   * @param class1
   * @param class2
   * @return
   * @throws IOException
   * @throws IllegalAccessException
   * @throws InstantiationException
   *
   * @see {@link #readAllValues(String, Class)}
   */
  public <E> MappingIterator<E> readValues(InputStream src, Class<E> itemsType) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(itemsType).readValues(encoded);
  }

  /**
   * @param out
   * @return
   * @throws UnsupportedEncodingException
   */
  public Reader getEncodedReader(InputStream out) throws UnsupportedEncodingException {

    return this.charset != null ? new InputStreamReader(out, this.charset) : new InputStreamReader(out);
  }

  /**
   * @param src
   * @param type
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  public <E> E readValue(File src, Class<E> type) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(type).readValue(encoded);
  }

  /**
   * @param src
   * @param class1
   * @param class2
   * @return
   * @throws IOException
   * @throws IllegalAccessException
   * @throws InstantiationException
   *
   * @see {@link #readAllValues(String, Class)}
   */
  public <E> MappingIterator<E> readValues(File src, Class<E> itemsType) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(itemsType).readValues(encoded);
  }

  /**
   * @param out
   * @return
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   */
  public Reader getEncodedReader(final File src) throws IOException {

    return getEncodedReader(new FileInputStream(src));
  }

  /**
   * @param src
   * @param type
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  public <E> E readValue(String src, Class<E> type) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(type).readValue(encoded);
  }

  /**
   * @param src
   * @param class1
   * @param class2
   * @return
   * @throws IOException
   * @throws IllegalAccessException
   * @throws InstantiationException
   *
   * @see {@link #readAllValues(String, Class)}
   */
  public <E> MappingIterator<E> readValues(String src, Class<E> itemsType) throws IOException {

    final Reader encoded = getEncodedReader(src);
    return readerFor(itemsType).readValues(encoded);
  }

  /**
   * @param out
   * @return
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   */
  public Reader getEncodedReader(final String src) throws UnsupportedEncodingException, FileNotFoundException {

    return getEncodedReader(new ByteArrayInputStream(src.getBytes()));
  }

  /**
   * @param out
   * @param type
   * @return
   * @return
   */
  public <E> ObjectWriter writerFor(E value) { // TODO save writers in CsvFormat fields (cache)

    // FIXME : handle null value
    return this.mapper.writerFor(value.getClass()).with(this.schema);
  }

  /**
   * Shortcut for :
   *
   * <pre>
   * this.writerFor(value).writeValue(this.getWriterForCharset(out), value)
   * </pre>
   *
   * @param out
   * @param value
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  public <E> void writeValue(OutputStream out, E value) throws IOException {

    if (this.schema.getColumnDesc().equals("]")) {
      throw new JsonGenerationException("No columns to write");
    }

    // FIXME delete if/then when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    if (!this.endingLineSeparator) {
      final String data = writeValueAsString(value);
      IOUtils.write(data, out, this.charset);
    } else {
      final Writer encoded = getEncodedWriter(out);
      this.writerFor(value).writeValue(encoded, value);
    }
  }

  /**
   * @param out
   * @return
   * @throws UnsupportedEncodingException
   */
  public Writer getEncodedWriter(OutputStream out) throws UnsupportedEncodingException {

    return this.charset != null ? new OutputStreamWriter(out, this.charset) : new OutputStreamWriter(out);
  }

  /**
   * Shortcut for :
   *
   * <pre>
   * this.writerFor(value).writeValue(resultFile, value)
   * </pre>
   *
   * @param value
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  public <E> void writeValue(final File resultFile, E value) throws IOException {

    if (this.schema.getColumnDesc().equals("]")) {
      throw new JsonGenerationException("No columns to write");
    }
    if (resultFile.getParentFile() != null) {
      resultFile.getParentFile().mkdirs();
    }
    // FIXME delete if/then when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    if (!this.endingLineSeparator) {
      final String data = writeValueAsString(value);
      FileUtils.writeStringToFile(resultFile, data, this.charset); // TODO may be replaced by java.nio.Files
    } else {
      final Writer encoded = getEncodedWriter(resultFile);
      this.writerFor(value).writeValue(encoded, value);
    }
  }

  /**
   * @param resultFile
   * @return
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   */
  public Writer getEncodedWriter(final File resultFile) throws IOException {

    return getEncodedWriter(new FileOutputStream(resultFile));
  }

  /**
   * Shortcut for :
   *
   * <pre>
   * this.writerFor(value).writeValueAsString(value)
   * </pre>
   *
   * @param value
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  public <E> String writeValueAsString(E value) throws IOException {

    if (this.schema.getColumnDesc().equals("]")) {
      throw new JsonGenerationException("No columns to write");
    }

    // FIXME delete var when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    String str = this.writerFor(value).writeValueAsString(value);

    // FIXME delete when Pull Request is merged : https://github.com/FasterXML/jackson-dataformats-text/pull/46
    if (!this.endingLineSeparator) {
      if (str.endsWith(getLineSeparatorString())) {
        str = str.substring(0, str.length() - this.schema.getLineSeparator().length);
      }
    }

    // if (this.charset != null) {
    // final Charset charset = Charset.forName(this.charset);
    // return charset.encode(str).toString(); // FIXME : we only want to encode string to the target charset => OK ?
    // } else {
    return str;
    // }
  }

}