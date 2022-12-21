package mk.ukim.finki.av1wp.model.exceptions;

public class PasswordDoNotMatchException extends RuntimeException{
    public PasswordDoNotMatchException() {
        super("Password do not match exception");
    }
}
