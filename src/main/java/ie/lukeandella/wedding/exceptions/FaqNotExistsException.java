package ie.lukeandella.wedding.exceptions;

public class FaqNotExistsException extends WeddingApplicationException{

    public FaqNotExistsException(String message){
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
