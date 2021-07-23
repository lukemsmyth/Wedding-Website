package ie.lukeandella.wedding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Gift")
@Table//(name = "gift")
@Accessors(fluent = false)
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

    //IMAGE
    @Column(name = "image", nullable = true)
    private String image;

    //LINK
    @Column(name = "link", nullable = true)
    private String link;

    //SPLIT-ABLE
    @Column(name = "splitable", nullable = false)
    private boolean splitable;

    //RESERVEES
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "gift_user",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> reservees = new HashSet<>();

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


    public Gift(Long id, String name, String description, Double price, String image, String link, Set<User> reservees, Integer percentageReserved, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.link = link;
        this.reservees = reservees;
        this.percentageReserved = percentageReserved;
        this.visible = visible;
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

    public Gift(String name, String description, Double price, String image, String link, boolean splitable, Set<User> reservees, Integer percentageReserved, boolean visible) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.link = link;
        this.splitable = splitable;
        this.reservees = reservees;
        this.percentageReserved = percentageReserved;
        this.visible = visible;
    }

    public Long getId() {
        return id;
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

    public Set<User> getReservees() {
        return reservees;
    }

    public void setReservees(Set<User> reservees) {
        this.reservees = reservees;
    }

    public void addReservee(User user){
        reservees.add(user);
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

    public boolean isSplitable() {
        return splitable;
    }

    public void setSplitable(boolean splitable) {
        this.splitable = splitable;
    }

//    public void toggleVisibility(){
//        if(isVisible()()){
//            setVisible(false);
//        }
//        setVisible(true);
//    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
//                ", reservees=" + reservees +
                ", percentageReserved=" + percentageReserved +
                ", visible=" + visible +
                '}';
    }
}
