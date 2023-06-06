package project1.bl;

import project1.dao.CompaniesDaoImpl;
import project1.dao.CouponsDaoImpl;
import project1.dao.CustomersDaoImpl;
import project1.exeptions.EmailOrPasswordAreWrongException;

import java.sql.SQLException;

/**
 * abstract class that the class's in the BL layer will extend from.
 * this is how they could use the dao layer, with the three objects:
 * CompaniesDaoImpl
 * CustomersDaoImpl
 * CouponsDaoImpl
 */
public abstract class ClientBL {
    protected CompaniesDaoImpl companiesDaoImpl = new CompaniesDaoImpl();
    protected CustomersDaoImpl customersDaoImpl = new CustomersDaoImpl();
    protected CouponsDaoImpl couponsDaoImpl = new CouponsDaoImpl();


    public abstract boolean login(String email, String password) throws SQLException, EmailOrPasswordAreWrongException;
}
