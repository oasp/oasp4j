package io.oasp.gastronomy.restaurant.test.general;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author arklos
 */
public class TestData {

  /**
   *
   * @author arklos
   */
  public static final class Additional {

    /***/
    public static final TableEto TABLE = createTable(null, 100L, null, TableState.FREE);

    /***/
    public static final MealEto MEAL = createMeal(null, "Hund", null);

    /***/
    public static final SideDishEto SIDE_DISH = createSideDish(null, "Katze", null);

    /***/
    public static final DrinkEto DRINK = createDrink(null, "Waesserchen", null, false);

    /***/
    public static final OfferEto OFFER = createOffer(null, "Hund-Katze-Maus-Menue", OfferState.NORMAL, 1L, 5L, 10L,
        null, new Money(6.99));

    /***/
    public static final OrderCto ORDER = createOrderCto(null, 1L, null, OrderState.CLOSED);

    /***/
    public static final OrderPositionEto ORDER_POSITION = createOrderPosition(null, "Schnitzel-Menü", "mit Ketschup",
        OrderPositionState.DELIVERED, 1L, null, new Money(6.99));

    /***/
    public static final BillEto BILL = createBill(null, true, null, new Money(14.98), new Money(1.4), 3L, 4L);

    /***/
    public static final StaffMemberEto STAFF_MEMEBR = createStaffmember(100L, Role.CHIEF, "Artur", "Chief", 0);

    /***/
    public static final TableEto CHANGED_TABLE_1 = createTable(101L, 1L, 1, TableState.FREE);

    /***/
    public static final MealEto CHANGED_MEAL_1 = createMeal(1L, "Maus", 1);

    /***/
    public static final SideDishEto CHANGED_SIDE_DISH_1 = createSideDish(5L, "Sommep", 1);

    /***/
    public static final DrinkEto CHANGED_DRINK_1 = createDrink(9L, "Waesserchen", 1, false);

    /***/
    public static final OfferEto CHANGED_OFFER_1 = createOffer(1L, "Schnitzel-Menü", OfferState.SPECIAL, 1L, 5L, 10L,
        1, new Money(8.99));

    /***/
    public static final OrderCto CHANGED_ORDER_1_CTO = createOrderCto(1L, 1L, 1, OrderState.OPEN);

    /***/
    public static final OrderPositionEto CHANGED_ORDER_POSITION_1 = createOrderPosition(1L, "Maus-Menü", "mit Hose",
        OrderPositionState.PAYED, 1L, 1, new Money(6.99));

    /***/
    public static final BillEto CHANGED_BILL_1 = createBill(1L, true, 1, new Money(15.98), new Money(2.3), 1L, 2L);

    /***/
    public static final StaffMemberEto CHANGED_STAFF_MEMEBR_1 =
        createStaffmember(0L, Role.CHIEF, "Charles", "Chief", 0);

  }

  /**
   *
   * @author arklos
   */
  public static final class DB {
    /***/
    public static final TableEto TABLE_1 = createTable(101L, 1L, 1, TableState.OCCUPIED);

    /***/
    public static final TableEto TABLE_2 = createTable(102L, 2L, 1, TableState.FREE);

    /***/
    public static final TableEto TABLE_3 = createTable(103L, 3L, 1, TableState.FREE);

    /***/
    public static final TableEto TABLE_4 = createTable(104L, 4L, 1, TableState.FREE);

    /***/
    public static final TableEto TABLE_5 = createTable(105L, 5L, 1, TableState.FREE);

    /***/
    public static final MealEto MEAL_1 = createMeal(1L, "Schnitzel", 1);

    /***/
    public static final MealEto MEAL_2 = createMeal(2L, "Goulasch", 1);

    /***/
    public static final MealEto MEAL_3 = createMeal(3L, "Pfifferlinge", 1);

    /***/
    public static final MealEto MEAL_4 = createMeal(4L, "Salat", 1);

    /***/
    public static final SideDishEto SIDE_DISH_1 = createSideDish(5L, "Pommes", 1);

    /***/
    public static final SideDishEto SIDE_DISH_2 = createSideDish(6L, "Reis", 1);

    /***/
    public static final SideDishEto SIDE_DISH_3 = createSideDish(7L, "Brot", 1);

    /***/
    public static final SideDishEto SIDE_DISH_4 = createSideDish(8L, "Knödel", 1);

    /***/
    public static final DrinkEto DRINK_1 = createDrink(9L, "Wasser", 1, false);

    /***/
    public static final DrinkEto DRINK_2 = createDrink(10L, "Cola", 1, false);

    /***/
    public static final DrinkEto DRINK_3 = createDrink(11L, "Bier", 1, false);

    /***/
    public static final DrinkEto DRINK_4 = createDrink(12L, "Wein / Apfelwein", 1, false);

    /***/
    public static final OfferEto OFFER_1 = createOffer(1L, "Schnitzel-Menü", OfferState.NORMAL, 1L, 5L, 10L, 1,
        new Money(6.99));

    /***/
    public static final OfferEto OFFER_2 = createOffer(2L, "Goulasch-Menü", OfferState.NORMAL, 2L, 6L, 11L, 1,
        new Money(7.99));

    /***/
    public static final OfferEto OFFER_3 = createOffer(4L, "Pfifferlinge-Menü", OfferState.NORMAL, 3L, 8L, 12L, 1,
        new Money(8.99));

    /***/
    public static final OfferEto OFFER_4 = createOffer(5L, "Salat-Menü", OfferState.NORMAL, 4L, 7L, 9L, 1, new Money(
        5.99));

    /***/
    public static final OrderEto ORDER_1 = createOrder(1L, 1L, 1, OrderState.CLOSED);

    /***/
    public static final OrderPositionEto ORDER_POSITION_1 = createOrderPosition(1L, "Schnitzel-Menü", "mit Ketschup",
        OrderPositionState.DELIVERED, 1L, 1, new Money(6.99));

    /***/
    public static final OrderPositionEto ORDER_POSITION_2 = createOrderPosition(2L, "Goulasch-Menü", "",
        OrderPositionState.DELIVERED, 1L, 1, new Money(7.99));

    /***/
    public static final OrderPositionEto ORDER_POSITION_3 = createOrderPosition(3L, "Pfifferlinge-Menü", "",
        OrderPositionState.DELIVERED, 1L, 1, new Money(8.99));

    /***/
    public static final OrderPositionEto ORDER_POSITION_4 = createOrderPosition(4L, "Salat-Menü", "",
        OrderPositionState.DELIVERED, 1L, 1, new Money(5.99));

    /***/
    public static final BillEto BILL_1 = createBill(1L, false, 1, new Money(14.98), new Money(1.3), 1L, 2L);

    /***/
    public static final BillEto BILL_2 = createBill(2L, true, 1, new Money(14.98), new Money(1.4), 3L, 4L);

    /***/
    public static final StaffMemberEto STAFF_MEMEBR_1 = createStaffmember(0L, Role.CHIEF, "Charly", "Chief", 0);

    /***/
    public static final StaffMemberEto STAFF_MEMEBR_2 = createStaffmember(1L, Role.COOK, "Carl", "Cook", 0);

    /***/
    public static final StaffMemberEto STAFF_MEMEBR_3 = createStaffmember(2L, Role.WAITER, "Willy", "Waiter", 0);

    /***/
    public static final StaffMemberEto STAFF_MEMEBR_4 = createStaffmember(3L, Role.BARKEEPER, "Bianca", "Barkeeper", 0);

    /***/
    public static final List<StaffMemberEto> ALL_STAFF_MEMBER = new LinkedList<>();

    /***/
    public static final List<BillEto> ALL_BILLS = new LinkedList<>();

    /***/
    public static final List<OrderPositionEto> ALL_ORDER_POSITIONS = new LinkedList<>();

    /***/
    public static final List<OrderEto> ALL_ORDERS = new LinkedList<>();

    /***/
    public static final List<OfferEto> ALL_OFFERS = new LinkedList<>();

    /***/
    public static final List<TableEto> ALL_TABLES = new LinkedList<>();

    /***/
    public static final List<MealEto> ALL_MEALS = new LinkedList<>();

    /***/
    public static final List<SideDishEto> ALL_SIDE_DISHES = new LinkedList<>();

    /***/
    public static final List<DrinkEto> ALL_DRINKS = new LinkedList<>();

    /***/
    public static final List<ProductEto> ALL_PRODUCTS = new LinkedList<>();

    // /***/
    // public static final StaffMemberEto[] ALL_STAFF_MEMBERS = { STAFF_MEMEBR_1, STAFF_MEMEBR_2, STAFF_MEMEBR_3,
    // STAFF_MEMEBR_4 };
    //
    // /***/
    // public static final BillEto[] ALL_BILLS = { BILL_1, BILL_2 };
    //
    // /***/
    // public static final OrderPositionEto[] ALL_ORDER_POSITIONS = { ORDER_POSITION_1, ORDER_POSITION_2,
    // ORDER_POSITION_3, ORDER_POSITION_4 };
    //
    // /***/
    // public static final OrderEto[] ALL_ORDERS = { ORDER_1 };
    //
    // /***/
    // public static final OfferEto[] ALL_OFFERS = { OFFER_1, OFFER_2, OFFER_3, OFFER_4 };
    //
    // /***/
    // public static final TableEto[] ALL_TABLES = { TABLE_1, TABLE_2, TABLE_3, TABLE_4, TABLE_5 };
    //
    // /***/
    // public static final MealEto[] ALL_MEALS = { MEAL_1, MEAL_2, MEAL_3, MEAL_4 };
    //
    // /***/
    // public static final SideDishEto[] ALL_SIDE_DISHES = { SIDE_DISH_1, SIDE_DISH_2, SIDE_DISH_3, SIDE_DISH_4 };
    //
    // /***/
    // public static final DrinkEto[] ALL_DRINKS = { DRINK_1, DRINK_2, DRINK_3, DRINK_4 };
    //
    // /***/
    // public static final ProductEto[] ALL_PRODUCTS = { MEAL_1, MEAL_2, MEAL_3, MEAL_4, SIDE_DISH_1, SIDE_DISH_2,
    // SIDE_DISH_3, SIDE_DISH_4, DRINK_1, DRINK_2, DRINK_3, DRINK_4 };

    /**
     * Exports DB Data to outputfile
     *
     * @param file Outputfile
     */
    public static final void exportData(String file) {

      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        init();
        writer.write("SET REFERENTIAL_INTEGRITY FALSE;\n\n");
        writer.write("TRUNCATE TABLE STAFFMEMBER;\n");
        writer.write("TRUNCATE TABLE BILL_ORDERPOSITION;\n");
        writer.write("TRUNCATE TABLE OFFER;\n");
        writer.write("TRUNCATE TABLE RestaurantTable;\n");
        writer.write("TRUNCATE TABLE BILL;\n");
        writer.write("TRUNCATE TABLE ORDERPOSITION;\n");
        writer.write("TRUNCATE TABLE TABLEORDER;\n");
        writer.write("TRUNCATE TABLE PRODUCT;\n\n");
        writer.write("SET REFERENTIAL_INTEGRITY TRUE;\n\n");

        for (TableEto e : ALL_TABLES) {
          writer.write("INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES ( "
              + getCsv(e.getId(), e.getNumber(), e.getModificationCounter(),
                  getPosition(e.getState(), TableState.values())) + " );\n");
        }

        writer.write("\n");

        for (MealEto e : ALL_MEALS) {
          writer.write("INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES ( "
              + getCsv(e.getId(), "Meal", e.getDescription(), e.getModificationCounter()) + " );\n");
        }

        writer.write("\n");

        for (SideDishEto e : ALL_SIDE_DISHES) {
          writer.write("INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES ( "
              + getCsv(e.getId(), "SideDish", e.getDescription(), e.getModificationCounter()) + " );\n");
        }

        writer.write("\n");

        for (DrinkEto e : ALL_DRINKS) {
          writer.write("INSERT INTO PRODUCT (id, dtype, description, modificationCounter, alcoholic) VALUES ( "
              + getCsv(e.getId(), "Drink", e.getDescription(), e.getModificationCounter(), e.isAlcoholic()) + " );\n");
        }

        writer.write("\n");

        for (OfferEto e : ALL_OFFERS) {
          writer
              .write("INSERT INTO OFFER (id, description, state, meal_id, sidedish_id, drink_id, modificationCounter, currentprice) VALUES ( "
                  + getCsv(e.getId(), e.getDescription(), getPosition(e.getState(), OfferState.values()),
                      e.getMealId(), e.getSideDishId(), e.getDrinkId(), e.getModificationCounter(), e.getPrice()
                          .getValue()) + " );\n");
        }

        writer.write("\n");

        for (OrderEto e : ALL_ORDERS) {
          writer.write("INSERT INTO TABLEORDER (id, tableid,  modificationCounter, state) VALUES ( "
              + getCsv(e.getId(), e.getTableId(), e.getModificationCounter(),
                  getPosition(e.getState(), OrderState.values())) + " );\n");
        }

        writer.write("\n");

        for (OrderPositionEto e : ALL_ORDER_POSITIONS) {
          writer
              .write("INSERT INTO ORDERPOSITION (id, offername, comment, state, order_Id, modificationCounter, price) VALUES ( "
                  + getCsv(e.getId(), e.getOfferName(), e.getComment(),
                      getPosition(e.getState(), OrderPositionState.values()), e.getOrderId(),
                      e.getModificationCounter(), e.getPrice().getValue()) + " );\n");
        }

        writer.write("\n");

        for (BillEto e : ALL_BILLS) {
          writer.write("INSERT INTO BILL (id, payed, modificationCounter, totalamount,tip) VALUES ( "
              + getCsv(e.getId(), e.isPayed(), e.getModificationCounter(), e.getTotal().getValue(), e.getTip()
                  .getValue()) + " );\n");
          for (Long id : e.getOrderPositionIds()) {
            writer.write("INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES ( "
                + getCsv(e.getId(), id) + " );\n");
          }
        }

        writer.write("\n");

        for (StaffMemberEto e : ALL_STAFF_MEMBER) {
          writer.write("INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES ( "
              + getCsv(e.getId(), e.getLastName().toLowerCase(), getPosition(e.getRole(), Role.values()),
                  e.getFirstName(), e.getLastName(), e.getModificationCounter()) + " );\n");
        }
        writer.flush();
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private static final int getPosition(Object o, Object[] values) {

      for (int i = 0; i < values.length; i++) {
        Object e = values[i];
        if (o.equals(e)) {
          return i;
        }
      }
      return -1;
    }

    private static final String getCsv(Object... values) {

      String csv = "";
      for (Object o : values) {
        if (o instanceof String) {
          csv += "'" + o + "' , ";
        } else {
          csv += o + " , ";
        }
      }
      csv = csv.substring(0, csv.length() - 2);
      return csv;
    }
  }

  @SuppressWarnings("javadoc")
  public static final OrderEto createOrder(Long id, Long tableid, Integer modificationCounter, OrderState state) {

    OrderEto order = new OrderEto();
    order.setId(id);
    order.setTableId(tableid);
    if (modificationCounter != null)
      order.setModificationCounter(modificationCounter);
    order.setState(state);
    return order;
  }

  @SuppressWarnings("javadoc")
  public static final OrderCto createOrderCto(Long id, Long tableid, Integer modificationCounter, OrderState state) {

    OrderCto cto = new OrderCto();
    cto.setOrder(createOrder(id, tableid, modificationCounter, state));
    return cto;
  }

  @SuppressWarnings("javadoc")
  public static final StaffMemberEto createStaffmember(Long id, Role role, String firstname, String lastname,
      Integer modificationCounter) {

    StaffMemberEto member = new StaffMemberEto();
    member.setId(id);
    member.setRole(role);
    member.setFirstName(firstname);
    member.setLastName(lastname);
    if (modificationCounter != null)
      member.setModificationCounter(modificationCounter);

    return member;
  }

  @SuppressWarnings("javadoc")
  public static final BillEto createBill(Long id, boolean payed, Integer modificationCounter, Money totalamount,
      Money tip, Long... orderPositions) {

    BillEto bill = new BillEto();
    bill.setId(id);
    bill.setPayed(payed);
    if (modificationCounter != null)
      bill.setModificationCounter(modificationCounter);
    bill.setTotal(totalamount);
    bill.setTip(tip);
    if (orderPositions != null) {
      bill.setOrderPositionIds(Arrays.asList(orderPositions));
    }
    return bill;
  }

  @SuppressWarnings("javadoc")
  public static final OrderPositionEto createOrderPosition(Long id, String offername, String comment,
      OrderPositionState state, Long order_Id, Integer modificationCounter, Money price) {

    OrderPositionEto position = new OrderPositionEto();
    position.setId(id);
    position.setOfferName(offername);
    position.setComment(comment);
    position.setState(state);
    position.setOrderId(order_Id);
    if (modificationCounter != null)
      position.setModificationCounter(modificationCounter);
    position.setPrice(price);
    return position;
  }

  @SuppressWarnings("javadoc")
  public static final TableEto createTable(Long id, Long number, Integer modificationCounter, TableState state) {

    TableEto table = new TableEto();
    if (id != null)
      table.setId(id);
    table.setNumber(number);
    if (modificationCounter != null)
      table.setModificationCounter(modificationCounter);
    table.setState(state);
    return table;
  }

  @SuppressWarnings("javadoc")
  public static final MealEto createMeal(Long id, String description, Integer modificationCounter) {

    MealEto meal = new MealEto();
    meal.setId(id);
    meal.setDescription(description);
    if (modificationCounter != null)
      meal.setModificationCounter(modificationCounter);
    return meal;
  }

  @SuppressWarnings("javadoc")
  public static final SideDishEto createSideDish(Long id, String description, Integer modificationCounter) {

    SideDishEto sideDish = new SideDishEto();
    sideDish.setId(id);
    sideDish.setDescription(description);
    if (modificationCounter != null)
      sideDish.setModificationCounter(modificationCounter);
    return sideDish;
  }

  @SuppressWarnings("javadoc")
  public static final DrinkEto createDrink(Long id, String description, Integer modificationCounter, boolean alcoholic) {

    DrinkEto drink = new DrinkEto();
    drink.setId(id);
    drink.setDescription(description);
    if (modificationCounter != null)
      drink.setModificationCounter(modificationCounter);
    drink.setAlcoholic(alcoholic);
    return drink;
  }

  @SuppressWarnings("javadoc")
  public static final OfferEto createOffer(Long id, String description, OfferState state, Long meal_id,
      Long sidedish_id, Long drink_id, Integer modificationCounter, Money currentprice) {

    OfferEto offer = new OfferEto();
    offer.setId(id);
    offer.setDescription(description);
    offer.setState(state);
    offer.setMealId(meal_id);
    offer.setSideDishId(sidedish_id);
    offer.setDrinkId(drink_id);
    if (modificationCounter != null)
      offer.setModificationCounter(modificationCounter);
    offer.setPrice(currentprice);
    return offer;
  }

  private static boolean initiated = false;

  /***/
  public static final void init() {

    if (!initiated) {
      DB.ALL_STAFF_MEMBER.addAll(searchForFields(DB.class, StaffMemberEto.class));
      DB.ALL_BILLS.addAll(searchForFields(DB.class, BillEto.class));
      DB.ALL_ORDER_POSITIONS.addAll(searchForFields(DB.class, OrderPositionEto.class));
      DB.ALL_ORDERS.addAll(searchForFields(DB.class, OrderEto.class));
      DB.ALL_OFFERS.addAll(searchForFields(DB.class, OfferEto.class));
      DB.ALL_TABLES.addAll(searchForFields(DB.class, TableEto.class));
      DB.ALL_MEALS.addAll(searchForFields(DB.class, MealEto.class));
      DB.ALL_SIDE_DISHES.addAll(searchForFields(DB.class, SideDishEto.class));
      DB.ALL_DRINKS.addAll(searchForFields(DB.class, DrinkEto.class));
      DB.ALL_PRODUCTS.addAll(DB.ALL_MEALS);
      DB.ALL_PRODUCTS.addAll(DB.ALL_SIDE_DISHES);
      DB.ALL_PRODUCTS.addAll(DB.ALL_DRINKS);
      initiated = true;
    }
  }

  @SuppressWarnings("unchecked")
  private static final <T> List<T> searchForFields(Class<?> source, Class<T> type) {

    List<T> list = new LinkedList<>();

    Field[] fields = source.getDeclaredFields();

    for (Field field : fields) {
      if (field.getType().equals(type)) {
        try {
          list.add((T) field.get(null));
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return list;

  }

}
