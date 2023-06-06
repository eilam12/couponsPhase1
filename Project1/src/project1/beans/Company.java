package project1.beans;

import java.util.List;

/**
 * Company class is the bean layer of a Company. it represents a Company in java and has all of it's parameters
 */
public class Company {
    private int id;
    private String name, email, password;
    private List<Coupon> coupons;

    /**
     * this method is a constructor for reading information from the database that includes all company's parameters
     *
     * @param id
     * @param name
     * @param email
     * @param password
     * @param coupons
     */
    public Company(int id, String name, String email, String password, List<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * this method is a constructor for creating new company in the database. it doesn't include id because this parameter is
     * autoincrement in the database, and doesn't include the company coupons because this parameter doesn't need to be put when
     * creating new company
     *
     * @param name
     * @param email
     * @param password
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}' + "\n";
    }
}
