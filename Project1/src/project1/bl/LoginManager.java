package project1.bl;

import project1.beans.ClientType;
import project1.exeptions.EmailOrPasswordAreWrongException;

import java.sql.SQLException;

public class LoginManager {

    private static LoginManager instance;

    private LoginManager() {
    }

    // to get the one object of LoginManager
    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    /**
     * this method is for login in the program as a client. it will return the clientType
     *
     * @param clientType is the type of client that is login in
     * @param email      is the email of the client
     * @param password   is password of the client
     * @return ClientBL Object
     * @throws EmailOrPasswordAreWrongException when email or password are wrong
     * @throws SQLException
     */
    public ClientBL login(ClientType clientType, String email, String password) throws EmailOrPasswordAreWrongException,
            SQLException {

        switch (clientType) {
            case ADMINISTRATOR:
                AdminBLImpl adminBL = new AdminBLImpl();
                if (adminBL.login(email, password))
                    return adminBL;
                break;

            case COMPANY:
                CompanyBLImpl companyBL = new CompanyBLImpl();
                if (companyBL.login(email, password))
                    return companyBL;
                break;

            case CUSTOMER:
                CustomerBLImpl customerBL = new CustomerBLImpl();
                if (customerBL.login(email, password))
                    return customerBL;
                break;
        }
        return null;
    }
}
