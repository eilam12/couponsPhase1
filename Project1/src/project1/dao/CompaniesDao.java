package project1.dao;

import project1.beans.Company;
import project1.exeptions.EmailOrPasswordAreWrongException;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDao {
    int isCompanyExists(String email, String password) throws SQLException;

    void addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException;

    void deleteCompany(int companyId) throws SQLException;

    Company getOneCompany(int companyId) throws SQLException;

    List<Company> getAllCompanies() throws SQLException;
}
