package ie.lukeandella.wedding.exceptions;

public class ItineraryNotExistsException extends WeddingApplicationException{

    public ItineraryNotExistsException(String message){
        super(message);
    }
}
