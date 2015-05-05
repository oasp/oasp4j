package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.reader;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * Implementation of ItemReader. It reads data from csv file and creates OfferEto.
 *
 * @author jczas
 */
public class OfferFromCsvReader extends FlatFileItemReader<OfferEto> {

  private static final Logger LOG = LoggerFactory.getLogger(OfferFromCsvReader.class);

  /**
   * The constructor.
   */
  public OfferFromCsvReader() {

    super();

    // setting resource
    // TODO set resource from parameters (???)
    setResource(new ClassPathResource("offer.csv"));

    // setting line tokenizer and mapper
    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    delimitedLineTokenizer.setNames(new String[] { "number" });

    BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<DrinkEto>();
    beanWrapperFieldSetMapper.setTargetType(OfferEto.class);

    DefaultLineMapper defaultLineMapper = new DefaultLineMapper<OfferEto>();
    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

    setLineMapper(defaultLineMapper);
  }
}
