package project1.beans;


import java.util.List;

/**
 * Customer class is the bean layer of a Customer. it represents a Customer in java and has all of it's parameters
 */
public class Customer {
    private int id;
    private String firstName, lastName, email, password;
    private List<Coupon> coupons;

    /**
     * this method is a constructor for reading information from the database that includes all customer's parameters
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param coupons
     */
    public Customer(int id, String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * this method is a constructor for creating new customer in the database. it doesn't include id because this parameter is
     * autoincrement in the database, and doesn't include the customer coupons because this parameter doesn't need to be put when
     * creating new customer
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}' + "\n";
    }
}
