package project1.dao;

import project1.beans.Customer;
import project1.exeptions.EmailOrPasswordAreWrongException;
import project1.exeptions.IdDoesNotExistException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    int isCustomerExist(String email, String password) throws SQLException, EmailOrPasswordAreWrongException;

    void addCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void deleteCustomer(int customerId) throws SQLException, IdDoesNotExistException;

    Customer getOneCustomer(int customerId) throws SQLException, IdDoesNotExistException;

    List<Customer> getAllCustomers() throws SQLException;
}
