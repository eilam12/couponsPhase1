package project1.dao;

import project1.beans.Coupon;
import project1.exeptions.CouponAmountIsZeroException;
import project1.exeptions.PurchaseAlreadyExistException;
import project1.exeptions.PurchaseDoesntExistException;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDao {
    void addCoupons(Coupon coupon) throws SQLException;

    void updateCoupons(Coupon coupon) throws SQLException;

    void deleteCoupons(int couponId) throws SQLException;

    Coupon getOneCoupon(int couponId) throws SQLException;

    List<Coupon> getAllCoupons() throws SQLException;

    void addCouponPurchase(int customerId, int couponId) throws SQLException, PurchaseAlreadyExistException, CouponAmountIsZeroException;

    void deleteCouponPurchase(int customerId, int couponId) throws SQLException, PurchaseDoesntExistException;
}
