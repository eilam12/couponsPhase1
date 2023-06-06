package project1.dao;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomersDaoImpl class is a class in the dao layer that with its methods we can connect to the database
 * in order to make changes in the database, like create read update and delete.
 * this class is for methods that relate to Customer.
 */
public class CustomersDaoImpl implements CustomerDao {
    ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * this method is for connecting to the database to check if customer exist by email and password
     *
     * @param email    to check if the email exist
     * @param password to check if the password exist
     * @return int so if resultSet.next() is true it will return the id of the company for further use. and if resultSet.next()
     * is false it will return -1 as an indicator to know that it is false
     * @throws SQLException
     */
    @Override
    public int isCustomerExist(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where " +
                    " email='" + email + "' and password='" + password + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : -1;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to add a new customer to the database
     *
     * @param customer is to add the information of the customer to the database
     * @throws SQLException
     */
    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into customers (first_name, last_name," +
                    " email, password) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next())
                customer.setId(res.getInt(1));

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to update a customer in the database
     *
     * @param customer is to update the information of the customer to the database
     * @throws SQLException
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update customers set " +
                    "first_name='" + customer.getFirstName() +
                    "', last_name='" + customer.getLastName() +
                    "', email='" + customer.getEmail() +
                    "', password='" + customer.getPassword() +
                    "' where id=" + customer.getId());
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete a customer in the database
     *
     * @param customerId is to know which company to delete. the method deletes a company by id
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from customers where id=" + customerId);
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get one customer from the database and its information for further use
     *
     * @param customerId is to know which customer to get from the database. the method pulls the customer by its id
     * @return Customer object. if resultSet.next() is false the method will return null as an indicator
     * that the customer was not found in the database
     * @throws SQLException
     */
    @Override
    public Customer getOneCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = new ArrayList<>();
            Customer customer;
            PreparedStatement statement1 = con.prepareStatement("select * from customers where id=" + customerId);
            ResultSet res1 = statement1.executeQuery();
            if (res1.next()) {
                customer = new Customer(res1.getInt(1), res1.getString(2), res1.getString(3),
                        res1.getString(4), res1.getString(5), coupons);
            } else
                return null;

            PreparedStatement statement2 = con.prepareStatement("select * from coupons join coupons_vs_customers on" +
                    " coupons.id=coupons_vs_customers.coupon_id where coupons_vs_customers.customer_id=" + customerId);
            ResultSet res2 = statement2.executeQuery();
            while (res2.next()) {
                coupons.add(new Coupon(res2.getInt(1), res2.getString(2), res2.getInt(3),
                        Category.values()[res2.getInt(4) - 1], res2.getString(5),
                        res2.getDate(6), res2.getDate(7), res2.getInt(8),
                        res2.getDouble(9), res2.getString(10)));
            }
//            customer.setCoupons(coupons);  // no need
            return customer;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get all customers from the database and its information for further use
     *
     * @return List of customers(Customer). if resultSet.next() is false the method will return an empty List.
     * each customer in the List will have an empty List of coupons because there is no need for all of that information in this case
     * @throws SQLException
     */
    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Customer> customers = new ArrayList<>();
            PreparedStatement statement1 = con.prepareStatement("select * from customers");
            ResultSet res1 = statement1.executeQuery();
            while (res1.next()) {
                customers.add(new Customer(res1.getInt(1), res1.getString(2), res1.getString(3),
                        res1.getString(4), res1.getString(5), new ArrayList<>())); // empty list because we don't need all the coupons of the customers when
                // getting all customers, its unnecessary information in this case
            }
            return customers;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if email is already exist in customers
     *
     * @param email is which email to check
     * @return boolean
     * @throws SQLException
     */
    public boolean isEmailExist(String email) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where " +
                    " email='" + email + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete purchases in the database by customerId
     *
     * @param customerId is to know which customer's purchases to delete. the method delete by customerId
     * @throws SQLException
     */
    public void deleteCustomerPurchases(int customerId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons_vs_customers where " +
                    "customer_id=" + customerId);
            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if email is already exist in customers when updating a customer.
     * need different method for that to check in the database without including the customer that we want to update
     *
     * @param customer the method get a Customer object to check with its id and email in the database
     * @return boolean
     * @throws SQLException
     */
    public boolean isEmailExistInCustomersForUpdate(Customer customer) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from customers where not id=" + customer.getId() +
                    " and email='" + customer.getEmail() + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }
}
