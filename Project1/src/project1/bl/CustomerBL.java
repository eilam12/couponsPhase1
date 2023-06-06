package project1.bl;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.exeptions.CouponAmountIsZeroException;
import project1.exeptions.CouponDateIsExpiredException;
import project1.exeptions.PurchaseAlreadyExistException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBL {
    void purchaseCoupon(Coupon coupon) throws SQLException, PurchaseAlreadyExistException, CouponAmountIsZeroException, CouponDateIsExpiredException;

    List<Coupon> getCustomerCoupons() throws SQLException;

    List<Coupon> getCustomerCouponsByCategory(Category category) throws SQLException;

    List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws SQLException;

    Customer getCustomerDetails() throws SQLException;
}
