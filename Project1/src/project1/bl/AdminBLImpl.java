package project1.bl;

import project1.beans.Company;
import project1.beans.Coupon;
import project1.beans.Customer;
import project1.exeptions.EmailAlreadyExistsException;
import project1.exeptions.EmailOrNameAlreadyExistsException;
import project1.exeptions.EmailOrPasswordAreWrongException;
import project1.exeptions.IdDoesNotExistException;

import java.sql.SQLException;
import java.util.List;

/**
 * AdminBlImpl class is a class in the BL layer that has the methods that the Admin can use, and this class implements the business
 * logic of the program in the methods. the class will be used as an object that lets the Admin make actions in the program
 */
public class AdminBLImpl extends ClientBL implements AdminBL {
    public AdminBLImpl() {
    }

    /**
     * this method is to check if email and password are correct when login in by an Administrator
     *
     * @param email    is the email to check if correct
     * @param password is the password to check if correct
     * @return boolean
     * @throws EmailOrPasswordAreWrongException when email or password are wrong to notify that login was failed
     */
    @Override
    public boolean login(String email, String password) throws EmailOrPasswordAreWrongException {
        if (email.equals("admin@admin.com") && password.equals("admin"))
            return true;
        else
            throw new EmailOrPasswordAreWrongException("Email or password are wrong");
    }

    /**
     * this method is to add a company with the business logic by using to the dao layer.
     * the method uses isNameOrEmailExist() method to check if the name or email of the company already exist
     * and companiesDaoImpl.addCompany() method to add the company to the database.
     *
     * @param company is the Company to add
     * @throws SQLException
     * @throws EmailOrNameAlreadyExistsException when isNameOrEmailExist() method return true
     */
    public void addCompany(Company company) throws SQLException, EmailOrNameAlreadyExistsException {
        if (companiesDaoImpl.isNameOrEmailExist(company.getName(), company.getEmail()))
            throw new EmailOrNameAlreadyExistsException("Email or name already exist");
        else
            companiesDaoImpl.addCompany(company);
    }

    /**
     * this method is to update a company with the business logic by using to the dao layer.
     * the method uses isEmailExistInCompaniesForUpdate() method to check if the email of the company already exist
     * and companiesDaoImpl.updateCompany() method to update the company.
     *
     * @param company is the company to update
     * @throws SQLException
     * @throws EmailAlreadyExistsException when isEmailExistInCompaniesForUpdate() method return true
     */
    @Override
    public void updateCompany(Company company) throws SQLException, EmailAlreadyExistsException {
        if (!companiesDaoImpl.isEmailExistInCompaniesForUpdate(company))
            companiesDaoImpl.updateCompany(company);
        else
            throw new EmailAlreadyExistsException("Email already exist");
    }

    /**
     * this method is to delete company after using and checking the business logic of the program.
     * to delete a company must need to delete its coupons first, and to delete coupons must need to delete the purchases
     * of those coupons first.
     *
     * @param companyId is to know which company to delete
     * @throws SQLException
     * @throws IdDoesNotExistException if no company was found by this companyId
     */
    @Override
    public void deleteCompany(int companyId) throws SQLException, IdDoesNotExistException {
        Company company = getOneCompany(companyId);
        for (Coupon c : company.getCoupons()) {
            couponsDaoImpl.deleteCouponPurchaseByCouponId(c.getId());
        }
        companiesDaoImpl.deleteCompanyCoupons(companyId);
        companiesDaoImpl.deleteCompany(companyId);
    }

    /**
     * this method is to get one company and its information for further use, using the dao layer.
     * the method uses companiesDaoImpl.getOneCompany() method from the dao layer to get the company from the database.
     *
     * @param companyId is to know which company to get
     * @return Company object
     * @throws SQLException
     * @throws IdDoesNotExistException when companiesDaoImpl.getOneCompany() return null
     */
    @Override
    public Company getOneCompany(int companyId) throws SQLException, IdDoesNotExistException {
        if (companiesDaoImpl.getOneCompany(companyId) == null)
            throw new IdDoesNotExistException("Company was not found");
        else
            return companiesDaoImpl.getOneCompany(companyId);
    }

    /**
     * this method is to get all companies and its information for further use, using the dao layer.
     * the method uses companiesDaoImpl.getAllCompanies() method from the dao layer to get the List of companies
     * from the database
     *
     * @return List off companies(Company)
     * @throws SQLException
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        return companiesDaoImpl.getAllCompanies();
    }

    /**
     * this method is to add a customer with the business logic by using to the DAO layer.
     * the method uses isEmailExist() method to check if the email of the customer already exist
     * and customersDaoImpl.addCustomer() method to add the customer to the database.
     *
     * @param customer is the Customer to add
     * @throws SQLException
     * @throws EmailAlreadyExistsException when isEmailExist() method return true
     */
    @Override
    public void addCustomer(Customer customer) throws SQLException, EmailAlreadyExistsException {
        if (customersDaoImpl.isEmailExist(customer.getEmail()))
            throw new EmailAlreadyExistsException("Email already exist");
        else
            customersDaoImpl.addCustomer(customer);
    }

    /**
     * this method is to update a customer with the business logic by using to the dao layer.
     * the method uses isEmailExistInCustomersForUpdate() method to check if the email of the customer already exist
     * and customersDaoImpl.updateCustomer() method to update the customer.
     *
     * @param customer is the customer to update
     * @throws SQLException
     * @throws EmailAlreadyExistsException when isEmailExistInCustomersForUpdate() return true
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException, EmailAlreadyExistsException {
        if (!customersDaoImpl.isEmailExistInCustomersForUpdate(customer))
            customersDaoImpl.updateCustomer(customer);
        else
            throw new EmailAlreadyExistsException("Email already exist");
    }

    /**
     * this method is to delete customer after using and checking the business logic of the program.
     * to delete a customer must need to delete its purchases first.
     *
     * @param customerId is to know which customer to delete
     * @throws SQLException
     * @throws IdDoesNotExistException from getOneCustomer() method
     */
    @Override
    public void deleteCustomer(int customerId) throws SQLException, IdDoesNotExistException {
        Customer customer = getOneCustomer(customerId); // to check if id exist
        customersDaoImpl.deleteCustomerPurchases(customer.getId());
        customersDaoImpl.deleteCustomer(customer.getId());
    }

    /**
     * this method is to get one customer and its information for further use, using the dao layer.
     * the method uses customersDaoImpl.getOneCustomer() method from the dao layer to get the customer from the database.
     *
     * @param customerId is to know which customer to get
     * @return Customer object
     * @throws SQLException
     * @throws IdDoesNotExistException when customersDaoImpl.getOneCustomer() return null
     */
    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, IdDoesNotExistException {
        if (customersDaoImpl.getOneCustomer(customerId) == null)
            throw new IdDoesNotExistException("Customer was not found");
        else
            return customersDaoImpl.getOneCustomer(customerId);
    }

    /**
     * this method is to get all customers and its information for further use, using the dao layer.
     * the method uses customersDaoImpl.getAllCustomers() method from the dao layer to get the List of customers
     * from the database
     *
     * @return List of customers(Customer)
     * @throws SQLException
     */
    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customersDaoImpl.getAllCustomers();
    }
}
