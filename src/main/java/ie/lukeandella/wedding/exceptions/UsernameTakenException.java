package ie.lukeandella.wedding.exceptions;

public class UsernameTakenException extends WeddingApplicationException{

    public UsernameTakenException(String message){
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
