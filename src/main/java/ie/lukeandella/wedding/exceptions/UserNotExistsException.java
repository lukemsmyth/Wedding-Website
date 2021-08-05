package ie.lukeandella.wedding.exceptions;

public class UserNotExistsException extends WeddingApplicationException{

    public UserNotExistsException(String message){
        super(message);
    }
}
