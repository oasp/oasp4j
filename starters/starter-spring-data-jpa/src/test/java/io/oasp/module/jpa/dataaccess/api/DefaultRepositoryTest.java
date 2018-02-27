package io.oasp.module.jpa.dataaccess.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import io.oasp.example.TestApplication;
import io.oasp.example.component.common.api.to.FooSearchCriteriaTo;
import io.oasp.example.component.dataaccess.api.FooEntity;
import io.oasp.example.component.dataaccess.api.FooRepository;
import io.oasp.module.basic.common.api.query.LikePatternSyntax;
import io.oasp.module.basic.common.api.query.StringSearchConfigTo;
import io.oasp.module.basic.common.api.query.StringSearchOperator;
import io.oasp.module.jpa.common.api.to.OrderByTo;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * Test of {@link FooRepository} in order to test the following infrastructure:
 * <ul>
 * <li>{@link io.oasp.module.jpa.dataaccess.api.data.DefaultRepository}</li>
 * <li>{@link io.oasp.module.jpa.dataaccess.api.data.GenericRepository}</li>
 * <li>{@link io.oasp.module.jpa.dataaccess.impl.data.GenericRepositoryFactoryBean}</li>
 * <li>{@link io.oasp.module.jpa.dataaccess.impl.data.GenericRepositoryImpl}</li>
 * </ul>
 */
@SpringBootTest(classes = { TestApplication.class }, webEnvironment = WebEnvironment.NONE)
public class DefaultRepositoryTest extends ComponentTest {

  @Inject
  private FooRepository fooRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private TransactionTemplate template;

  private <T> T doInTx(TransactionCallback<T> lambda) {

    return this.template.execute(lambda);
  }

  /**
   * Test of {@link io.oasp.module.jpa.dataaccess.api.data.GenericRepository#forceIncrementModificationCounter(Object)}.
   * Ensures that the modification counter is updated after the call of that method when the transaction is closed.
   */
  @Test
  public void testForceIncrementModificationCounter() {

    // given
    FooEntity entity = doInTx((x) -> newFoo("Foo"));

    // when
    FooEntity entity2 = doInTx((x) -> {
      FooEntity foo = this.fooRepository.find(entity.getId());
      this.fooRepository.forceIncrementModificationCounter(foo);
      return foo;
    });

    // then
    assertThat(entity.getModificationCounter()).isEqualTo(0);
    assertThat(entity2.getModificationCounter()).isEqualTo(1);
  }

  private FooEntity newFoo(String message) {

    return newFoo(message, message);
  }

  private FooEntity newFoo(String message, String name) {

    FooEntity entity = new FooEntity();
    entity.setMessage(message);
    entity.setName(name);
    this.fooRepository.save(entity);
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getModificationCounter()).isEqualTo(0);
    assertThat(this.fooRepository.find(entity.getId())).isSameAs(entity);
    return entity;
  }

  /**
   * Test of {@link io.oasp.module.jpa.dataaccess.api.data.GenericRepository#forceIncrementModificationCounter(Object)}.
   * Ensures that the modification counter is updated after the call of that method when the transaction is closed.
   */
  @Test
  @Transactional
  public void testFindByMessage() {

    this.fooRepository.deleteAll();

    // given
    int hitsPerPage = 5;
    int totalPages = 3;
    int pageNumber = totalPages - 1;
    String message = "Foo";
    int totalElements = hitsPerPage * totalPages - 1;
    for (int i = 0; i < totalElements; i++) {
      newFoo(message, createName(i));
    }
    Sort sort = JpaSort.unsafe(Direction.ASC, "name");
    Pageable pageable = new PageRequest(pageNumber, hitsPerPage, sort);

    // when
    Page<FooEntity> page = this.fooRepository.findByMessage(message, pageable);

    // then
    assertThat(page.getNumber()).isEqualTo(pageNumber);
    assertThat(page.getNumberOfElements()).isEqualTo(hitsPerPage - 1);
    assertThat(page.getSize()).isEqualTo(hitsPerPage);
    assertThat(page.getTotalPages()).isEqualTo(totalPages);
    assertThat(page.getTotalElements()).isEqualTo(totalElements);
    assertThat(page.getContent()).hasSize(page.getNumberOfElements());
    int i = pageNumber * hitsPerPage;
    for (FooEntity foo : page.getContent()) {
      assertThat(foo.getName()).isEqualTo(createName(i));
      i++;
    }
  }

  private String createName(int i) {

    return "Name" + String.format("%03d", i);
  }

  /**
   * Test of {@link io.oasp.module.jpa.dataaccess.api.data.GenericRepository#forceIncrementModificationCounter(Object)}.
   * Ensures that the modification counter is updated after the call of that method when the transaction is closed.
   */
  @Test
  @Transactional
  public void testFindByCriteria() {

    this.fooRepository.deleteAll();

    // given
    FooSearchCriteriaTo criteria = new FooSearchCriteriaTo();
    criteria.setMessage("T?st");
    StringSearchConfigTo config = StringSearchConfigTo.of(LikePatternSyntax.GLOB);
    config.setIgnoreCase(true);
    config.setMatchSubstring(true);
    criteria.setMessageOption(config);
    criteria.addSort(new OrderByTo("message", OrderDirection.DESC));
    List<String> values = new ArrayList<>(Arrays.asList("MY_TEST", "Sometest", "Test", "Testing", "Xtest"));
    Collections.shuffle(values);
    for (String message : values) {
      newFoo(message);
    }
    newFoo("Aaa");
    newFoo("Xxx");

    // when
    PaginatedListTo<FooEntity> hits = this.fooRepository.findByCriteria(criteria);

    // then
    Collections.sort(values, (x, y) -> -x.compareTo(y));
    assertThat(hits.getResult().stream().map(x -> x.getMessage()).collect(Collectors.toList()))
        .containsExactlyElementsOf(values);

    // and when
    config.setOperator(StringSearchOperator.NOT_LIKE);
    hits = this.fooRepository.findByCriteria(criteria);

    // then
    assertThat(hits.getResult().stream().map(x -> x.getMessage()).collect(Collectors.toList())).containsExactly("Xxx",
        "Aaa");

    // and when
    config.setOperator(StringSearchOperator.LIKE);
    config.setIgnoreCase(false);
    hits = this.fooRepository.findByCriteria(criteria);

    // then
    assertThat(hits.getResult().stream().map(x -> x.getMessage()).collect(Collectors.toList()))
        .containsExactly("Testing", "Test");

    // and when
    config.setMatchSubstring(false);
    hits = this.fooRepository.findByCriteria(criteria);

    // then
    assertThat(hits.getResult().stream().map(x -> x.getMessage()).collect(Collectors.toList())).containsExactly("Test");

    // and when
    criteria.setMessage("Test");
    config.setLikeSyntax(null);
    config.setOperator(StringSearchOperator.NE);
    criteria.setSort(Arrays.asList(new OrderByTo("message")));
    hits = this.fooRepository.findByCriteria(criteria);

    // then
    assertThat(hits.getResult().stream().map(x -> x.getMessage()).collect(Collectors.toList())).containsExactly("Aaa",
        "MY_TEST", "Sometest", "Testing", "Xtest", "Xxx");
  }

};
