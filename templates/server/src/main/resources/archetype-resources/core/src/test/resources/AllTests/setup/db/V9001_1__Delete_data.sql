SET FOREIGN_KEY_CHECKS=0;

DELETE FROM RestaurantTable;
DELETE FROM RestaurantOrder;
DELETE FROM Product;
DELETE FROM Offer;
DELETE FROM OrderPosition;
DELETE FROM Bill;

--Keep Staffmembers to allow authentication
--DELETE FROM StaffMember;

SET FOREIGN_KEY_CHECKS=1;
