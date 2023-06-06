package project1.exeptions;

public class EmailOrNameAlreadyExistsException extends Exception {
    public EmailOrNameAlreadyExistsException(String message) {
        super(message);

    }
}
