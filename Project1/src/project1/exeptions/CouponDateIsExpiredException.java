package project1.exeptions;

public class CouponDateIsExpiredException extends Exception {
    public CouponDateIsExpiredException(String message) {
        super(message);
    }
}
