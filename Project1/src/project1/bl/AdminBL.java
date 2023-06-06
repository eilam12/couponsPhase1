package project1.bl;

import project1.beans.Company;
import project1.beans.Customer;
import project1.exeptions.EmailAlreadyExistsException;
import project1.exeptions.EmailOrNameAlreadyExistsException;
import project1.exeptions.EmailOrPasswordAreWrongException;
import project1.exeptions.IdDoesNotExistException;

import java.sql.SQLException;
import java.util.List;

public interface AdminBL {
    boolean login(String email, String password) throws EmailOrPasswordAreWrongException;

    void addCompany(Company company) throws SQLException, EmailOrNameAlreadyExistsException;

    void updateCompany(Company company) throws SQLException, EmailAlreadyExistsException;

    void deleteCompany(int companyId) throws SQLException, IdDoesNotExistException;

    Company getOneCompany(int companyId) throws SQLException, IdDoesNotExistException;

    List<Company> getAllCompanies() throws SQLException;

    void addCustomer(Customer customer) throws SQLException, EmailAlreadyExistsException;

    void updateCustomer(Customer customer) throws SQLException, EmailAlreadyExistsException;

    void deleteCustomer(int customerId) throws SQLException, IdDoesNotExistException;

    Customer getOneCustomer(int customerId) throws SQLException, IdDoesNotExistException;

    List<Customer> getAllCustomers() throws SQLException;
}
