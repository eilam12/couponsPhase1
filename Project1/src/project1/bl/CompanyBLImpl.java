package project1.bl;

import project1.beans.Category;
import project1.beans.Company;
import project1.beans.Coupon;
import project1.exeptions.CompanyIdDoesntMachTheCompanyException;
import project1.exeptions.EmailOrPasswordAreWrongException;
import project1.exeptions.IdDoesNotExistException;
import project1.exeptions.TitleAlreadyExistException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CompanyBLImpl class is a class in the BL layer that has the methods that a Company can use, and this class implements the business
 * logic of the program in the methods. the class will be used as an object that lets a Company make actions in the program
 */
public class CompanyBLImpl extends ClientBL implements CompaniesBL {
    private int companyId;

    public CompanyBLImpl() {
    }

    /**
     * this method is to check if email and password are correct when login in by a Company
     * companiesDaoImpl.isCompanyExists() method returns int and if it is not -1 than it is the id of the login company
     * and companyId of this class will be the return value of companiesDaoImpl.isCompanyExists() method
     *
     * @param email    is the email to check if correct
     * @param password is the password to check if correct
     * @return boolean
     * @throws SQLException
     * @throws EmailOrPasswordAreWrongException when companiesDaoImpl.isCompanyExists() return -1 to
     *                                          notify that login was failed
     */
    @Override
    public boolean login(String email, String password) throws SQLException, EmailOrPasswordAreWrongException {
        if (companiesDaoImpl.isCompanyExists(email, password) == -1)
            throw new EmailOrPasswordAreWrongException("Email or password are wrong");
        else {
            companyId = companiesDaoImpl.isCompanyExists(email, password);
            return true;
        }
    }

    /**
     * this method is to add a coupon with the business logic by using to the dao layer.
     * the method check if coupon.companyId is equal to companyId. and if the coupon title already exist in the same
     * company using the dao layer.
     *
     * @param coupon is the coupon to add
     * @throws SQLException
     * @throws TitleAlreadyExistException    when isCouponTitleAlreadyExistInSameCompany() method return true
     * @throws CompanyIdDoesntMachTheCompanyException when coupon.companyId is not equal to companyId
     */
    @Override
    public void addCoupon(Coupon coupon) throws SQLException, TitleAlreadyExistException, CompanyIdDoesntMachTheCompanyException {
        if (coupon.getCompanyId() != companyId)
            throw new CompanyIdDoesntMachTheCompanyException("The coupon.companyId does not match the company id");
        if (couponsDaoImpl.isCouponTitleAlreadyExistInSameCompany(coupon.getTitle(), coupon.getCompanyId()))
            throw new TitleAlreadyExistException("Title already exist");
        else
            couponsDaoImpl.addCoupons(coupon);
    }

    /**
     * this method is to update a coupon with the business logic by using to the dao layer.
     * and if the coupon title already exist in the same company using the dao layer.
     *
     * @param coupon is the coupon to update
     * @throws SQLException
     * @throws TitleAlreadyExistException when isCouponTitleAlreadyExistInSameCompanyForUpdate() return true
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException, TitleAlreadyExistException {
        if (!couponsDaoImpl.isCouponTitleAlreadyExistInSameCompanyForUpdate(coupon))
            couponsDaoImpl.updateCoupons(coupon);
        else
            throw new TitleAlreadyExistException("Title already exist");
    }

    /**
     * this method is to delete a coupon with the business logic by using to the dao layer.
     * to delete coupons must need to delete the purchases of those coupons first.
     *
     * @param couponId to know which purchases to delete
     * @throws SQLException
     * @throws IdDoesNotExistException when couponsDaoImpl.getOneCoupon() method return null
     */
    @Override
    public void deleteCoupon(int couponId) throws SQLException, IdDoesNotExistException {
        if (couponsDaoImpl.getOneCoupon(couponId) == null)
            throw new IdDoesNotExistException("Coupon was not found");
        else {
            couponsDaoImpl.deleteCouponPurchaseByCouponId(couponId);
            couponsDaoImpl.deleteCoupons(couponId);
        }
    }

    /**
     * this method is to get all coupons of a specific company using the dao layer.
     *
     * @return List of coupons(Coupon).
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCompanyCoupons() throws SQLException {
        return couponsDaoImpl.getCompanyCoupons(companyId);
    }

    /**
     * this method is to get all coupons of a specific company by specific category using the dao layer.
     * the method get all the company coupons and checks which is in the specific category and filter the List by that.
     *
     * @param category is the category to filter the coupons by
     * @return List of coupons(Coupon)
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCompanyCouponsByCategory(Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon c : getCompanyCoupons()) {
            if (c.getCategory().equals(category))
                coupons.add(c);
        }
        return coupons;
    }

    /**
     * this method is to get all coupons of a specific company until maximum price using the dao layer.
     * the method get all the company coupons and checks which cost until(including) the maximum price and filter the List by that.
     *
     * @param maxPrice is the price to filter the coupons by
     * @return List of coupons(Coupon)
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon c : getCompanyCoupons()) {
            if (c.getPrice() <= maxPrice)
                coupons.add(c);
        }
        return coupons;
    }

    /**
     * this method is to get the company details using the dao layer, and the companyId that was got from the login
     *
     * @return Company object
     * @throws SQLException
     */
    @Override
    public Company getCompanyDetails() throws SQLException {
        return companiesDaoImpl.getOneCompany(companyId);
    }
}
