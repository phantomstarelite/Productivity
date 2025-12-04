// Custom checked exception (Inheritance from Exception)
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}