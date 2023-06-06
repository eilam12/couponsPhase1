package project1.exeptions;

public class TitleAlreadyExistException extends Exception {
    public TitleAlreadyExistException(String message) {
        super(message);
    }
}
