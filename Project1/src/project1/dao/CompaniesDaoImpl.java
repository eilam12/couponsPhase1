package project1.dao;

import project1.beans.Category;
import project1.beans.Coupon;
import project1.db.ConnectionPool;
import project1.beans.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CompaniesDaoImpl class is a class in the dao layer that with its methods we can connect to the database
 * in order to make changes in the database, like create read update and delete.
 * this class is for methods that relate to Company.
 */
public class CompaniesDaoImpl implements CompaniesDao {
    ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * this method is for connecting to the database to check if company exist by email and password
     *
     * @param email    to check if the email exist
     * @param password to check if the password exist
     * @return int so if resultSet.next() is true it will return the id of the company for further use. and if resultSet.next()
     * is false it will return -1 as an indicator to know that it is false
     * @throws SQLException
     */
    @Override
    public int isCompanyExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where" +
                    " email='" + email + "' and password='" + password + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : -1;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to add a new company to the database
     *
     * @param company is to add the information of the company to the database
     * @throws SQLException
     */
    @Override
    public void addCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("insert into companies(name, email, password) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                company.setId(resultSet.getInt(1));

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to update a company in the database
     *
     * @param company is to update the information of the company to the database
     * @throws SQLException
     */
    @Override
    public void updateCompany(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("update companies set" +
                    " email='" + company.getEmail() + "', password='" + company.getPassword() +
                    "' where id=" + company.getId());

            statement.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete a company in the database
     *
     * @param companyId is to know which company to delete. the method deletes a company by id
     * @throws SQLException
     */
    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement2 = con.prepareStatement("delete from companies where id=" + companyId);
            statement2.execute();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get one company from the database and its information for further use
     *
     * @param companyId is to know which company to get from the database. the method pulls the company by its id
     * @return Company object. if resultSet.next() is false the method will return null as an indicator
     * that the company not found in the database
     * @throws SQLException
     */
    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Coupon> coupons = new ArrayList<>();
            Company company = null;

            PreparedStatement statement2 = con.prepareStatement("select * from companies where id=" + companyId);
            ResultSet res2 = statement2.executeQuery();
            if (res2.next()) {
                company = new Company(res2.getInt(1), res2.getString(2),
                        res2.getString(3), res2.getString(4), coupons);
            } else
                return null;

            PreparedStatement statement1 = con.prepareStatement("select * from coupons where company_id=" + companyId);
            ResultSet res1 = statement1.executeQuery();
            while (res1.next()) {
                coupons.add(new Coupon(res1.getInt(1), res1.getString(2), res1.getInt(3),
                        Category.values()[res1.getInt(4) - 1], res1.getString(5),
                        res1.getDate(6), res1.getDate(7), res1.getInt(8),
                        res1.getDouble(9), res1.getString(10)));
            }
//            company.setCoupons(coupons); // no need
            return company;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to get all companies from the database and its information for further use
     *
     * @return List of companies(Company). if resultSet.next() is false the method will return an empty List.
     * each company in the List will have an empty List of coupons because there is no need for all of that information in this case
     * @throws SQLException
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        Connection con = pool.getConnection();
        try {
            List<Company> companies = new ArrayList<>();
            PreparedStatement statement1 = con.prepareStatement("select * from companies");
            ResultSet res1 = statement1.executeQuery();
            while (res1.next()) {
                companies.add(new Company(res1.getInt(1), res1.getString(2),
                        res1.getString(3), res1.getString(4), new ArrayList<>()));
            }
            return companies;

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to delete the company coupons
     *
     * @param companyId is to know which coupons to delete. the method deletes coupons by companyId
     * @throws SQLException
     */
    public void deleteCompanyCoupons(int companyId) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("delete from coupons where company_id=" + companyId);
            statement.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if there is already company with the same name or email
     *
     * @param name  is the company name
     * @param email is the company email
     * @return boolean as an answer whether there is already a company with the same name or email. return true even if only
     * one parameter exist
     * @throws SQLException
     */
    public boolean isNameOrEmailExist(String name, String email) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where" +
                    " name='" + name + "' or email='" + email + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }

    /**
     * this method is for connecting to the database to check if there is already a company with the same email when updating
     * a company. need different method for that to check in the database without including the company that we want to update
     *
     * @param company the method get a Company object to check with its id and email in the database
     * @return boolean
     * @throws SQLException
     */
    public boolean isEmailExistInCompaniesForUpdate(Company company) throws SQLException {
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement("select * from companies where not id=" + company.getId() +
                    " and email='" + company.getEmail() + "'");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } finally {
            pool.restoreConnection(con);
        }
    }
}
