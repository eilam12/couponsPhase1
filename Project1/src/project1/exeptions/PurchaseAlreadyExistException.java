package project1.exeptions;

public class PurchaseAlreadyExistException extends Exception {
    public PurchaseAlreadyExistException(String message) {
        super(message);
    }
}
