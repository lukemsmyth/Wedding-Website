package ie.lukeandella.wedding.exceptions;

public class GiftNotExistsException extends WeddingApplicationException{

    public GiftNotExistsException(String message){
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
