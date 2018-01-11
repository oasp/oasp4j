package io.oasp.module.basic.csv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.oasp.module.test.common.base.ModuleTest;

/**
 * @author MLAVIGNE
 *
 */
@SuppressWarnings("javadoc")
public class CsvFormatTest extends ModuleTest {

  private CsvFormat base = new CsvFormat();

  private CsvFormat twoColumns = this.base.withColumns("code,comment").withNullValue("");

  private static final String defaultLineSeparator = "\n";

  private static final String windowsLineSeparator = "\r\n";

  @JsonFilter(CsvFormat.FILTER)
  public static class Eto {
    private Long id;

    private String code;

    private String comment;

    private String other;

    public Date date;

    public Long getId() {

      return this.id;
    }

    public void setId(Long id) {

      this.id = id;
    }

    public String getCode() {

      return this.code;
    }

    public void setCode(String code) {

      this.code = code;
    }

    public String getComment() {

      return this.comment;
    }

    public void setComment(String comment) {

      this.comment = comment;
    }

    public String getOther() {

      return this.other;
    }

    public void setOther(String other) {

      this.other = other;
    }

    public Date getDate() {

      return this.date;
    }

    public void setDate(Date date) {

      this.date = date;
    }

  }

  @JsonInclude(JsonInclude.Include.NON_NULL) // on ne renvoie pas de propriété null (NON_EMPTY => suppr "", 0, 0L)
  public static class EtoSkipNull extends Eto {

  }

  @JsonFilter(CsvFormat.FILTER)
  public static class Cto {
    private String title;

    public Date date;

    private Eto eto1;

    private Eto eto2;

    public String getTitle() {

      return this.title;
    }

    public void setTitle(String title) {

      this.title = title;
    }

    public Eto getEto1() {

      return this.eto1;
    }

    public void setEto1(Eto eto1) {

      this.eto1 = eto1;
    }

    public Eto getEto2() {

      return this.eto2;
    }

    public void setEto2(Eto eto2) {

      this.eto2 = eto2;
    }

  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumns(java.lang.String)}.
   */
  @Test(expected = JsonGenerationException.class)
  public void testImmutableFormatNoColumnsException() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    this.base.withColumns("code,comment"); // a new format is returned and base is unchanged (like my heart)
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    this.base.writeValue(out, eto);
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumns(java.lang.String)}.
   */
  @Test
  public void testWithColumnsString() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumns("code,comment");

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumns(java.lang.String)}.
   */
  @Test
  public void testWithANullColumn() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumns(Arrays.asList("code", null, "comment"));

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumns(java.lang.String)}.
   */
  @Test(expected = JsonGenerationException.class)
  public void testWithNullColumns() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumns((List<String>) null);

    final StringBuilder sb = new StringBuilder();
    sb.append("").append(defaultLineSeparator);
    format.writeValueAsString(eto);
  }

  /**
   * @param eto
   * @param format
   * @return
   * @throws JsonGenerationException
   * @throws JsonMappingException
   * @throws IOException
   */
  private AbstractCharSequenceAssert<?, String> assertThatWrittenContent(final Eto eto, final CsvFormat format)
      throws Exception {

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    format.writeValue(out, eto);
    final AbstractCharSequenceAssert<?, String> assertThatWrittenContent = assertThat(out.toString());
    return assertThatWrittenContent;
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withNullValue(java.lang.String)}.
   */
  @Test
  public void testWithNullValue() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumns("code,comment").withNullValue("NULL");

    final StringBuilder sb = new StringBuilder();
    sb.append("NULL,comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#readValue(java.io.InputStream, java.lang.Class)}.
   */
  @Test
  public void testWithNullValueSkipEto() throws Exception {

    final StringBuilder sb = new StringBuilder();
    sb.append(",23/10/2017").append(defaultLineSeparator);

    final CsvFormat format =
        this.twoColumns.withNullValue("").withColumns("code,date").withDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

    final ByteArrayInputStream src = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
    final EtoSkipNull eto = format.readValue(src, EtoSkipNull.class);

    assertThat(eto).isNotNull();
    assertThat(eto.getCode()).isNull();
    assertThat(eto.getComment()).isNull();
    assertThat(eto.getDate()).isEqualTo("2017-10-23");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withHeader(java.lang.String)}.
   */
  @Test
  public void testWithHeaderString() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withHeader("code,comment");

    final StringBuilder sb = new StringBuilder();
    sb.append("code,comment").append(defaultLineSeparator);
    sb.append(",comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());

    final CsvFormat format2 = format.withoutHeader();

    final StringBuilder sb2 = new StringBuilder();
    sb2.append(",comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format2).isEqualTo(sb2.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumnSeparator(char)}.
   */
  @Test
  public void testWithColumnSeparator() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumnSeparator(';').withHeader("code;comment");

    final StringBuilder sb = new StringBuilder();
    sb.append("code;comment").append(defaultLineSeparator);
    sb.append(";comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withLineSeparator(java.lang.String)}.
   */
  @Test
  public void testWithLineSeparator() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.twoColumns.withLineSeparator("\r\n");

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value").append(windowsLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withEndingLineSeparator()}.
   */
  @Test
  public void testWithEndingLineSeparator() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.twoColumns.withLineSeparator("\r\n").withoutEndingLineSeparator();

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value");
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withEndingLineSeparator()}.
   */
  @Test
  public void testWithEndingLineSeparator4Coverage() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.twoColumns.withoutEndingLineSeparator().withColumns("code,comment");

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value");
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());

    final CsvFormat format2 =
        this.twoColumns.withoutEndingLineSeparator().withColumns("code,comment").withEndingLineSeparator();

    final StringBuilder sb2 = new StringBuilder();
    sb2.append(",comment_value").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format2).isEqualTo(sb2.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withQuoteChar(char)}.
   */
  @Test
  public void testWithQuoteChar() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value1");

    final CsvFormat format = this.twoColumns.withQuoteChar('Q');

    final StringBuilder sb = new StringBuilder();
    sb.append(",Qcomment_value1Q").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withQuoteChar(char)}.
   */
  @Test
  public void testWithoutQuoteChar() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value1");

    final CsvFormat format = this.twoColumns.withoutQuoteChar();

    final StringBuilder sb = new StringBuilder();
    sb.append(",comment_value1").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withCharset(java.lang.String)}.
   */
  @Test
  @Ignore // TODO
  public void testWithCharset() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("é€£");

    final CsvFormat format = this.twoColumns.withCharset("ISO-8859-15");

    final StringBuilder sb = new StringBuilder();
    fail("Not yet implemented");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withDateFormat(java.text.DateFormat)}.
   */
  @Test
  public void testWithDateFormatWrite() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value1");
    final Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2017, 9, 23);
    eto.setDate(cal.getTime());

    final CsvFormat format =
        this.twoColumns.withColumns("code,date").withDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

    final StringBuilder sb = new StringBuilder();
    sb.append(",23/10/2017").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withDateFormat(java.text.DateFormat)}.
   */
  @Test
  public void testWithDateFormat() throws Exception {

    final CsvFormat format = this.twoColumns.withColumns("code,date").withDateFormats("dd/MM/yyyy");

    assertThat(format.readValue("CODE1,22/05/1987", Eto.class).getDate()).isEqualTo("1987-05-22");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withDateFormat(java.text.DateFormat)}.
   */
  @Test
  public void testWithDateFormats() throws Exception {

    final CsvFormat format = this.twoColumns.withColumns("code,date").withDateFormats("dd/MM/yyyy", "dd-MM-yyyy");

    assertThat(format.readValue("CODE1,22/05/1987", Eto.class).getDate()).isEqualTo("1987-05-22");
    assertThat(format.readValue("CODE1,22-05-1987", Eto.class).getDate()).isEqualTo("1987-05-22");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withDateFormat(java.text.DateFormat)}.
   */
  @Test(expected = JsonMappingException.class)
  public void testWithDateFormatsKO() throws Exception {

    final CsvFormat format = this.twoColumns.withColumns("code,date").withDateFormats("dd/MM/yyyy", "dd-MM-yyyy");

    assertThat(format.readValue("CODE1,22/05/1987", Eto.class).getDate()).isEqualTo("1987-05-22");
    assertThat(format.readValue("CODE1,22-05-1987", Eto.class).getDate()).isEqualTo("1987-05-22");
    format.readValue("CODE1,22|05|1987", Eto.class); // unknown format
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withDateFormat(java.text.DateFormat)}.
   */
  @Test
  public void testWithDateFormatsWrite() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value1");
    final Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2017, 9, 23);
    eto.setDate(cal.getTime());

    final CsvFormat format =
        this.twoColumns.withColumns("code,date").withDateFormats("dd/MM/yyyy", "dd-MM-yyyy");

    final StringBuilder sb = new StringBuilder();
    sb.append(",23/10/2017").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withoutDateFormat()}.
   */
  @Test
  public void testWithoutDateFormat() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value1");
    final Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2017, 9, 23);
    eto.setDate(cal.getTime());

    final CsvFormat format =
        this.twoColumns.withColumns("code,date").withDateFormat(new SimpleDateFormat("dd/MM/yyyy")).withoutDateFormat();

    final StringBuilder sb = new StringBuilder();
    sb.append(",1508709600000").append(defaultLineSeparator);
    assertThatWrittenContent(eto, format).isEqualTo(sb.toString());
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#readValue(java.io.InputStream, java.lang.Class)}.
   */
  @Test
  public void testReadValue() throws Exception {

    final StringBuilder sb = new StringBuilder();
    sb.append(",23/10/2017").append(defaultLineSeparator);

    final CsvFormat format =
        this.twoColumns.withColumns("code,date").withDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

    final ByteArrayInputStream src = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
    final Eto eto = format.readValue(src, Eto.class);

    assertThat(eto).isNotNull();
    assertThat(eto.getDate()).isEqualTo("2017-10-23");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#readValue(java.io.InputStream, java.lang.Class)}.
   */
  @Test
  public void testReadValues() throws Exception {

    final StringBuilder sb = new StringBuilder();
    sb.append(",23/10/2017").append(defaultLineSeparator);
    sb.append("code2,24/10/2017").append(defaultLineSeparator);

    final CsvFormat format =
        this.twoColumns.withColumns("code,date").withDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

    final List<Eto> etos = format.readValues(sb.toString(), Eto.class).readAll();

    assertThat(etos).isNotNull().isNotEmpty();
    assertThat(etos.get(0).getDate()).isEqualTo("2017-10-23");
    assertThat(etos.get(1).getCode()).isEqualTo("code2");
    assertThat(etos.get(1).getDate()).isEqualTo("2017-10-24");
  }

  /**
   * Test method for
   * {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#withColumns(java.lang.String)}.
   */
  @Test
  public void testGetColumns() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("comment_value");

    final CsvFormat format = this.base.withColumns("code,comment");
    assertThat(format.getColumns()).containsOnly("code", "comment");
    assertThat(format.getColumns().get(0)).isEqualTo("code");
    assertThat(format.getColumns().get(1)).isEqualTo("comment");

    final CsvFormat format2 = format.withColumns((String) null);
    assertThat(format2.getColumns()).isNull();
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   */
  @Test
  public void testWriteValueAsString() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final CsvFormat format =
        this.base.withoutEndingLineSeparator().withColumns("code,comment")/* .withCharset("UTF-8") */;

    assertThat(format.writeValueAsString(eto)).isEqualTo(",héh€h£");
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   */
  @Test
  public void testWriteValueFile() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final CsvFormat format = this.base.withoutEndingLineSeparator().withColumns("code,comment").withCharset("UTF-8");

    final File resultFile = File.createTempFile("CsvFormatTest", ".csv");

    try {
      format.writeValue(resultFile, eto);
      assertThat(resultFile).exists();

      final String csv = FileUtils.readFileToString(resultFile, format.getCharset());
      assertThat(csv).isEqualTo(",héh€h£");
    } finally {
      resultFile.deleteOnExit();
      resultFile.delete();
    }
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   *
   * @see #testWriteValueFile()
   */
  @Test
  public void testReadValueFile() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final CsvFormat format =
        this.base.withNullValue("").withoutEndingLineSeparator().withColumns("code,comment").withCharset("UTF-8");

    final File resultFile = File.createTempFile("CsvFormatTest", ".csv");

    try {
      format.writeValue(resultFile, eto);
      final Eto readEto = format.readValue(resultFile, Eto.class);
      assertThat(readEto).isEqualToComparingFieldByField(eto);
    } finally {
      resultFile.deleteOnExit();
      resultFile.delete();
    }
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   *
   * @see #testWriteValueFile()
   */
  @Test
  public void testReadNullValue() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final CsvFormat format =
        this.base.withNullValue("NULL").withoutEndingLineSeparator().withHeader("code,comment").withCharset("UTF-8");

    final Eto readEto = format.readValue("code,comment" + defaultLineSeparator + "NULL,\"héh€h£\"", Eto.class);
    assertThat(readEto).isEqualToComparingFieldByField(eto);
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   *
   * @see #testWriteValueFile()
   */
  @Test
  public void testReadEmptyValue() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final CsvFormat format =
        this.base.withNullValue("").withoutEndingLineSeparator().withHeader("code,comment").withCharset("UTF-8");

    final Eto readEto = format.readValue("code,comment" + defaultLineSeparator + ",\"héh€h£\"", Eto.class);
    assertThat(readEto).isEqualToComparingFieldByField(eto);
  }

  /**
   * Test method for {@link com.orange.grace.traducteur.general.service.impl.rest.CsvFormat#writeValueAsString(Object)}.
   *
   * @see #testWriteValueFile()
   */
  @Test
  public void testReadValueFileTwoTimes() throws Exception {

    final Eto eto = new Eto();
    eto.setComment("héh€h£");

    final Eto eto2 = new Eto();
    eto2.setComment("héyh€yh£y");

    final CsvFormat format =
        this.base.withNullValue("").withoutEndingLineSeparator().withColumns("code,comment").withCharset("UTF-8");

    final File resultFile = File.createTempFile("CsvFormatTest", ".csv");

    try {
      format.writeValue(resultFile, eto);
      final Eto readEto = format.readValue(resultFile, Eto.class);
      assertThat(readEto).isEqualToComparingFieldByField(eto);

      format.writeValue(resultFile, eto2);
      final Eto readEto2 = format.readValue(resultFile, Eto.class);
      assertThat(readEto2).isEqualToComparingFieldByField(eto2);
    } finally {
      resultFile.deleteOnExit();
      resultFile.delete();
    }
  }

}
