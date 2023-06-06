package project1.beans;

import java.sql.Date;

/**
 * Coupon class is the bean layer of a Coupon. it represents a Coupon in java and has all of it's parameters.
 */
public class Coupon {
    private int id;
    private String title;
    private int companyId;
    private Category category;
    private String description;
    private Date startDate, endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * this method is a constructor for reading information from the database that includes all coupon's parameters
     *
     * @param id
     * @param title
     * @param companyId
     * @param category
     * @param description
     * @param startDate
     * @param endDate
     * @param amount
     * @param price
     * @param image
     */
    public Coupon(int id, String title, int companyId, Category category, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        this.id = id;
        this.title = title;
        this.companyId = companyId;
        this.category = category;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * this method is a constructor for creating new coupon in the database. it doesn't include id because this parameter is
     * autoincrement in the database
     *
     * @param title
     * @param companyId
     * @param category
     * @param description
     * @param startDate
     * @param endDate
     * @param amount
     * @param price
     * @param image
     */
    public Coupon(String title, int companyId, Category category, String description, Date startDate, Date endDate,
                  int amount, double price, String image) {
        this.title = title;
        this.companyId = companyId;
        this.category = category;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCompanyId() {
        return companyId;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", companyId=" + companyId +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}' + "\n";
    }
}
