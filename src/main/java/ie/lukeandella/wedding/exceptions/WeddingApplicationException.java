package ie.lukeandella.wedding.exceptions;

public class WeddingApplicationException extends Exception{

    public WeddingApplicationException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
