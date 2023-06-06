package project1.exeptions;

public class EmailOrPasswordAreWrongException extends Exception {
    public EmailOrPasswordAreWrongException(String message) {
        super(message);
    }
}
