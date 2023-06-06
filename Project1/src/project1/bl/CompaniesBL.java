package project1.bl;

import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.exeptions.CompanyIdDoesntMachTheCompanyException;
import project1.exeptions.IdDoesNotExistException;
import project1.exeptions.TitleAlreadyExistException;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesBL {
    void addCoupon(Coupon coupon) throws SQLException, TitleAlreadyExistException, CompanyIdDoesntMachTheCompanyException;

    void updateCoupon(Coupon coupon) throws SQLException, TitleAlreadyExistException;

    void deleteCoupon(int couponId) throws SQLException, IdDoesNotExistException;

    List<Coupon> getCompanyCoupons() throws SQLException;

    List<Coupon> getCompanyCouponsByCategory(Category category) throws SQLException;

    List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException;

    Company getCompanyDetails() throws SQLException;
}
