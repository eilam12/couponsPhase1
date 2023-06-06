package project1.threads;

import project1.beans.Coupon;
import project1.dao.CouponsDaoImpl;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * the CouponExpirationDailyJob class is a class that implements Runnable to create a thread that has a logic for this program.
 */
public class CouponExpirationDailyJob implements Runnable {
    private CouponsDaoImpl couponsDao = new CouponsDaoImpl();
    private boolean quit;


    public CouponExpirationDailyJob() {
    }

    /**
     * An Override for the method run().
     * the method go through the coupons once a day to check and delete the coupons that already expired
     */
    @Override
    public void run() {
        while (!quit) {
            try {
                for (Coupon c : couponsDao.getAllCoupons()) {
                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.HOUR_OF_DAY, 11);
                    now.set(Calendar.MINUTE, 59);

                    if (c.getEndDate().before(now.getTime())) {
                        couponsDao.deleteCouponPurchaseByCouponId(c.getId());
                        couponsDao.deleteCoupons(c.getId());
                    }
                }
                try {
                    Thread.sleep(1000 * 60 * 60 * 24);
                } catch (InterruptedException e) {
                }
            } catch (SQLException e) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    /**
     * this method is used to stop the thread
     */
    public void stop() {
        quit = true;
    }
}
