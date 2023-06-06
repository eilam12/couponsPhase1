package project1.exeptions;

public class CouponAmountIsZeroException extends Exception {
    public CouponAmountIsZeroException(String message) {
        super(message);
    }
}
