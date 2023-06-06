package project1.exeptions;

public class PurchaseDoesntExistException extends Exception {
    public PurchaseDoesntExistException(String message) {
        super(message);
    }
}
