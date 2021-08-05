package ie.lukeandella.wedding.exceptions;

public class RoleNotExistsException extends WeddingApplicationException{

    public RoleNotExistsException(String message){
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
