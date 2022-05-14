package pro.sky.exceptions;

public class ItemIsAbsentException extends RuntimeException {

    public ItemIsAbsentException(String message) {
        super(message);
    }
}