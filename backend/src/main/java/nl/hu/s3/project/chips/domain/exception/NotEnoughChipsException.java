package nl.hu.s3.project.chips.domain.exception;

public class NotEnoughChipsException extends RuntimeException {
    public NotEnoughChipsException(String message) {
        super(message);
    }
}
