package project1.dao;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CouponsDaoImpl class is a class in the dao layer that with its methods we can connect to the database
 * in order to make changes in the database, like create read update and delete.
 * this class is for methods that relate to Coupon.
 */
public class CouponsDaoImpl implements CouponsDao {
    ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * this method is for connecting to the database to add a new coupon to the database
     *
     * @param coupon is to add the information of the coupon to the database
     * @throws SQLException
     */
    @Override
    public void addCoupons(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into coupons (title, company_id, category_id, " +
                            "description, start_date, end_date, amount, price, image) values (?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coupon.getTitle());
            statement.setInt(2, coupon.getCompanyId());
            statement.setInt(3, (coupon.getCategory().ordinal() + 1));
            statement.setString(4, coupon.getDescription());
            statement.setString(5, coupon.getStartDate().toString());
            statement.setString(6, coupon.getEndDate().toString());
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                coupon.setId(resultSet.getInt(1));

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to update a coupon in the database
     *
     * @param coupon is to update the information of the coupon to the database
     * @throws SQLException
     */
    @Override
    public void updateCoupons(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update coupons set" +
                    " title='" + coupon.getTitle() +
                    "', category_id='" + (coupon.getCategory().ordinal() + 1) +
                    "', description='" + coupon.getDescription() +
                    "', start_date='" + coupon.getStartDate().toString() +
                    "', end_date='" + coupon.getEndDate().toString() +
                    "', amount='" + coupon.getAmount() +
                    "', price='" + coupon.getPrice() +
                    "', image='" + coupon.getImage() +
                    "' where id=" + coupon.getId());
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete a coupon in the database
     *
     * @param couponId is to know which coupon to delete. the method deletes a coupon by id
     * @throws SQLException
     */
    @Override
    public void deleteCoupons(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement2 = con.prepareStatement("delete from coupons where id=" + couponId);
            statement2.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get one coupon from the database and its information for further use
     *
     * @param couponId is to know which coupon to get from the database. the method pulls the coupon by its id
     * @return Coupon object. if resultSet.next() is false the method will return null as an indicator that
     * the coupon was not found in the database
     * @throws SQLException
     */
    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            Coupon coupon = null;
            PreparedStatement statement = con.prepareStatement("select * from coupons where id=" + couponId);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                coupon = createCoupon(res);
            }
            return coupon;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get all coupons from the database and its information for further use
     *
     * @return List of coupons(Coupon). if resultSet.next() is false the method will return an empty List
     * @throws SQLException
     */
    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement("select * from coupons");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                coupons.add(createCoupon(res));
            }
            return coupons;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get all coupons of a specific company from the database and its
     * information for further use
     *
     * @param companyId is to know which coupons to get from the database. the method pulls the coupons by its companyId.
     * @return List of coupons(Coupon) of the specific company.
     * if resultSet.next() is false the method will return an empty List
     * @throws SQLException
     */
    public List<Coupon> getCompanyCoupons(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement("select * from coupons where company_id=" + companyId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                coupons.add(createCoupon(res));
            }
            return coupons;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get all coupons of a specific customer(the coupons that he purchased)
     * from the database and its information for further use
     *
     * @param customerId is to know which coupons to get from the database. the method pulls the coupons with a join query
     *                   using customerId.
     * @return List of coupons(Coupon) of the specific company.
     * if resultSet.next() is false the method will return an empty List
     * @throws SQLException
     */
    public List<Coupon> getCustomerCoupons(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement("select * from coupons join coupons_vs_customers on" +
                    " coupons.id=coupons_vs_customers.coupon_id where coupons_vs_customers.customer_id=" + customerId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                coupons.add(createCoupon(res));
            }
            return coupons;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to add a coupon purchase in the database.
     *
     * @param customerId is to know which customer is making the purchase
     * @param couponId   is to know which coupon the customer purchased
     * @throws SQLException
     */
    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into coupons_vs_customers (customer_id, coupon_id) " +
                    "values (?,?)");
            statement.setInt(1, customerId);
            statement.setInt(2, couponId);
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete a coupon purchase in the database.
     *
     * @param customerId is to know from which customer to delete the purchase
     * @param couponId   is to know which purchase should be deleted form the customer purchases
     * @throws SQLException
     */
    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons_vs_customers where " +
                    "customer_id=" + customerId + " and coupon_id=" + couponId);
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if purchase already exist.
     * the method uses customerId and couponId to connect the purchase that need to be checked
     *
     * @param customerId is to know which customer to check
     * @param couponId   is to know which coupon to check
     * @return boolean
     * @throws SQLException
     */
    public boolean isPurchaseAlreadyExist(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons_vs_customers where " +
                    "customer_id=" + customerId + " and coupon_id=" + couponId);
            ResultSet res = statement.executeQuery();
            return res.next();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete purchases in the database by couponId
     *
     * @param couponId is to know which purchases to delete. the method delete by couponId
     * @throws SQLException
     */
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons_vs_customers where " +
                    "coupon_id=" + couponId);
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if there is already a coupon with the same title in the same company
     *
     * @param title     is to know which title to check
     * @param companyId is to know in which company to check
     * @return boolean
     * @throws SQLException
     */
    public boolean isCouponTitleAlreadyExistInSameCompany(String title, int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where title='" +
                    title + "' and company_id=" + companyId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if there is already a coupon with the same title
     * in the same company when updating a coupon.
     * need different method for that to check in the database without including the coupon that we want to update
     *
     * @param coupon the method get a Coupon object to check with its id, title and companyId in the database
     * @return boolean
     * @throws SQLException
     */
    public boolean isCouponTitleAlreadyExistInSameCompanyForUpdate(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from coupons where not id=" + coupon.getId() +
                    " and (title='" + coupon.getTitle() + "' and company_id=" + coupon.getCompanyId() + ")");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for creating a coupon. the method is used to short the code and make it more comfortable to read
     * because this act returns a few times in the code
     *
     * @param res is the resultSet to use in the method get the parameters
     * @return Coupon object
     * @throws SQLException
     */
    public Coupon createCoupon(ResultSet res) throws SQLException {
        return new Coupon(res.getInt(1), res.getString(2), res.getInt(3),
                Category.values()[res.getInt(4) - 1], res.getString(5),
                res.getDate(6), res.getDate(7), res.getInt(8),
                res.getDouble(9), res.getString(10));
    }
}
