package project1.tests;

import project1.beans.ClientType;
import project1.bl.*;
import project1.db.ConnectionPool;
import project1.threads.CouponExpirationDailyJob;

public class Test {
    public static void main(String[] args) {
        testAll();
    }

    public static void testAll() {
//        CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
//        Thread thread = new Thread(couponExpirationDailyJob);
//        thread.start();

//        try {
//            AdminBLImpl adminBL = (AdminBLImpl) LoginManager.getInstance().login(ClientType.ADMINISTRATOR, "admin@admin.com", "admin");
//            adminBL.login();
//            adminBL.addCompany();
//            adminBL.updateCompany();
//            adminBL.deleteCompany();
//            adminBL.getAllCompanies();
//            adminBL.addCustomer();
//            adminBL.updateCustomer();
//            adminBL.deleteCustomer();
//            adminBL.getOneCustomer();
//            adminBL.getAllCustomers();
//
//            CompanyBLImpl companyBL = (CompanyBLImpl) LoginManager.getInstance().login(ClientType.COMPANY, "", "");
//            companyBL.login();
//            companyBL.addCoupon();
//            companyBL.updateCoupon();
//            companyBL.deleteCoupon();
//            companyBL.getCompanyCoupons();
//            companyBL.getCompanyCouponsByCategory();
//            companyBL.getCompanyCouponsByMaxPrice();
//            companyBL.getCompanyDetails();
//
//            CustomerBLImpl customerBL = (CustomerBLImpl) LoginManager.getInstance().login(ClientType.CUSTOMER, "", "");
//            customerBL.login();
//            customerBL.purchaseCoupon();
//            customerBL.getCustomerCoupons();
//            customerBL.getCustomerCouponsByCategory();
//            customerBL.getCustomerCouponsByMaxPrice();
//            customerBL.getCustomerDetails();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        couponExpirationDailyJob.stop();
//        thread.interrupt();
//        ConnectionPool.getInstance().closeAllConnections();
    }
}
