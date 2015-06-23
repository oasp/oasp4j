package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport.reader;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

/**
 * Implementation of ItemReader. It reads data from csv file and creates DrinkEto.
 *
 * @author jczas
 */
public class DrinkReader extends FlatFileItemReader<DrinkEto> {

  private static final Logger LOG = LoggerFactory.getLogger(DrinkReader.class);

  /**
   * The constructor.
   */
  public DrinkReader() {

    super();
    // setting line tokenizer and mapper
    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    delimitedLineTokenizer.setNames(new String[] { "name", "description", "pictureId", "alcoholic" });

    BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<DrinkEto>();
    beanWrapperFieldSetMapper.setTargetType(DrinkEto.class);

    DefaultLineMapper defaultLineMapper = new DefaultLineMapper<DrinkEto>();
    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

    setLineMapper(defaultLineMapper);
  }

}
