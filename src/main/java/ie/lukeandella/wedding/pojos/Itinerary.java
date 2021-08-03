package ie.lukeandella.wedding.pojos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Itinerary")
@Table(name = "itinerary")
public class Itinerary implements Comparable<Itinerary>{

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itinerary_id")
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    //LOCATION
    @Column(name = "location")
    private String location;

    //TITLE
    @Column(name = "title")
    private String title;

    //DESCRIPTION
    @Column(name = "description")
    private String description;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Itinerary() {
    }

    public Itinerary(LocalDateTime dateTime, String location, String title, String description, LocalDateTime lastUpdated) {
        this.dateTime = dateTime;
        this.location = location;
        this.title = title;
        this.description = description;
        this.lastUpdated = lastUpdated;
    }

    //Order them by date so that they are displayed in logical order in the browser.
    @Override
    public int compareTo(Itinerary it) {
        return getDateTime().compareTo(it.getDateTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
