package project1.bl;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.exeptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * CustomerBLImpl class is a class in the BL layer that has the methods that a Customer can use, and this class implements the business
 * logic of the program in the methods. the class will be used as an object that lets a Customer make actions in the program
 */
public class CustomerBLImpl extends ClientBL implements CustomerBL {

    private int customerId;

    public CustomerBLImpl() {
    }

    /**
     * this method is to check if email and password are correct when login in by a Customer.
     * customersDaoImpl.isCustomerExist() method returns int and if it is not -1 than it is the id of the login customer
     * and customerId of this class will be the return value of customersDaoImpl.isCustomerExist() method
     *
     * @param email    is the email to check if correct
     * @param password is the password to check if correct
     * @return boolean
     * @throws SQLException
     * @throws EmailOrPasswordAreWrongException when customersDaoImpl.isCustomerExist() return -1 to
     *                                          notify that login was failed
     */
    @Override
    public boolean login(String email, String password) throws SQLException, EmailOrPasswordAreWrongException {
        if (customersDaoImpl.isCustomerExist(email, password) == -1)
            throw new EmailOrPasswordAreWrongException("Email or password are wrong");
        else {
            customerId = customersDaoImpl.isCustomerExist(email, password);
            return true;
        }
    }

    /**
     * this method is to add a purchase of a coupon by a customer with the business logic by using the dao layer.
     * the method checks if the purchase is valid according to the business logic before sending it to the dao.
     * after the purchase happening the coupon amount needs to be updated in the database, the method
     * couponsDaoImpl.updateCoupons() is for that
     *
     * @param coupon is the coupon of the purchase.
     * @throws SQLException
     * @throws PurchaseAlreadyExistException when couponsDaoImpl.isPurchaseAlreadyExist() method return true.
     * @throws CouponAmountIsZeroException   when coupon.getAmount <= 0.
     * @throws CouponDateIsExpiredException  whet coupon.getEndDate is already past.
     */
    @Override
    public void purchaseCoupon(Coupon coupon) throws SQLException, PurchaseAlreadyExistException,
            CouponAmountIsZeroException, CouponDateIsExpiredException {
        if (coupon.getAmount() <= 0)
            throw new CouponAmountIsZeroException("This coupon is no longer available");
        if (couponsDaoImpl.isPurchaseAlreadyExist(customerId, coupon.getId()))
            throw new PurchaseAlreadyExistException("Purchase Already Exist");
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 11);
        now.set(Calendar.MINUTE, 59);
        if (coupon.getEndDate().before(now.getTime()))
            throw new CouponDateIsExpiredException("Coupon date is expired");
        couponsDaoImpl.addCouponPurchase(customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsDaoImpl.updateCoupons(coupon);
    }

    /**
     * this method is to get all coupons that a specific customer purchased using the dao layer.
     *
     * @return List of customers(Customer).
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCustomerCoupons() throws SQLException {
        return couponsDaoImpl.getCustomerCoupons(customerId);
    }

    /**
     * this method is to get all coupons that a specific customer purchased of a specific category using the dao layer.
     * the method get all the customer coupons and checks which is in the specific category and filter the List by that.
     *
     * @param category is the category to filter the coupons by
     * @return List of coupons(Coupon)
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCustomerCouponsByCategory(Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon c : getCustomerCoupons()) {
            if (c.getCategory().equals(category))
                coupons.add(c);
        }
        return coupons;
    }

    /**
     * this method is to get all coupons that a specific customer purchased until maximum price using the dao layer.
     * the method get all the customer coupons and checks which cost until(including) the maximum price and filter the List by that.
     *
     * @param maxPrice is the price to filter the coupons by
     * @return List of coupons(Coupon)
     * @throws SQLException
     */
    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        for (Coupon c : getCustomerCoupons()) {
            if (c.getPrice() <= maxPrice)
                coupons.add(c);
        }
        return coupons;
    }

    /**
     * this method is to delete a customer purchase. before deleting a purchase there is a check if the purchase really exist
     * so there is an option to notify if the purchase don't really exist.
     * after deleting the purchase the coupon amount needs to be updated in the database, the method
     * couponsDaoImpl.updateCoupons() is for that
     *
     * @param couponId is to know which coupon needs to be deleted from the customer purchases.
     * @throws SQLException
     * @throws PurchaseDoesntExistException when couponsDaoImpl.isPurchaseAlreadyExist() method return false
     */
    public void deleteCustomerPurchase(int couponId) throws SQLException, PurchaseDoesntExistException {
        if (couponsDaoImpl.isPurchaseAlreadyExist(customerId, couponId)) {
            couponsDaoImpl.deleteCouponPurchase(customerId, couponId);
            Coupon coupon = couponsDaoImpl.getOneCoupon(couponId);
            coupon.setAmount(coupon.getAmount() + 1);
            couponsDaoImpl.updateCoupons(coupon);
        } else
            throw new PurchaseDoesntExistException("Purchase was not found");
    }

    /**
     * this method gets all the coupons so the customer could get the list of coupons and make purchases from that.
     *
     * @return List of coupons(Coupon)
     * @throws SQLException
     */
    public List<Coupon> getAllCoupons() throws SQLException {
        return couponsDaoImpl.getAllCoupons();
    }

    /**
     * this method is to get the customer details using the dao layer, and the customerId that was got from the login
     *
     * @return Customer object
     * @throws SQLException
     */
    @Override
    public Customer getCustomerDetails() throws SQLException {
        return customersDaoImpl.getOneCustomer(customerId);
    }
}
