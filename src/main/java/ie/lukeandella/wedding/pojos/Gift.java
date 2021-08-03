package ie.lukeandella.wedding.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Gift")
@Table(name = "gift")
public class Gift {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gift_id")
    private Long id;

    //NAME
    @Column(name = "name", nullable = true)
    private String name;

    //DESCRIPTION
    @Column(name = "description", nullable = true)
    private String description;

    //PRICE
    @Column(name = "price", nullable = true)
    private Double price;

    //RESERVATIONS
    @JsonIgnore
    @OneToMany(mappedBy = "gift")
    private Set<Reservation> reservations = new HashSet<>();

    //IMAGE
    @Column(name = "image", nullable = true)
    private String image;

    //LINK
    @Column(name = "link", nullable = true)
    private String link;

    //SPLIT-ABLE
    @Column(name = "splitable", nullable = false)
    private boolean splitable;

    //PERCENTAGE_RESERVED
    @Column(name = "percentage_reserved", nullable = false)
    private Integer percentageReserved;

    //VISIBLE
    @Column(name = "visible", nullable = false)
    private boolean visible = true;

    public Gift(){}

    public Gift(Long id){
        this.id = id;
    }

    public Gift(String name, String description, Double price, String image, String link, boolean splitable, Integer percentageReserved, boolean visible) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.link = link;
        this.splitable = splitable;
        this.percentageReserved = percentageReserved;
        this.visible = visible;
    }

    //Can be used to add or remove a reservation
    public void updateReservations(Reservation reservation){
        Set<Reservation> rs = getReservations();
        if(rs.contains(reservation)){
            rs.remove(reservation);
        }else{
            rs.add(reservation);
        }

        setReservations(rs);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isSplitable() {
        return splitable;
    }

    public void setSplitable(boolean splitable) {
        this.splitable = splitable;
    }

    public Integer getPercentageReserved() {
        return percentageReserved;
    }

    public void setPercentageReserved(Integer percentageReserved) {
        this.percentageReserved = percentageReserved;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", reservations=" + reservations +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", splitable=" + splitable +
                ", percentageReserved=" + percentageReserved +
                ", visible=" + visible +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gift gift = (Gift) o;
        return Objects.equals(id, gift.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
