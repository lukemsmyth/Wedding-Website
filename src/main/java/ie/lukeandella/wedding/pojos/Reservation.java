package ie.lukeandella.wedding.pojos;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Reservation")
@Table(name = "reservation")
public class Reservation {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private Long id;

    //PERCENTAGE RESERVED
    @Column(name = "percentage")
    private Integer percentage;

    //ACTIVE - set to true when res is made, set to false when res is cancelled
    @Column(name = "active")
    private boolean active;

    //GIFT
    @ManyToOne(cascade = CascadeType.ALL)  //many instances of reservation can be mapped to one instance of gift.
    @JoinColumn(name = "gift_id")
    private Gift gift;

    //USER
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Reservation() {
    }

    public Reservation(Integer percentage, boolean active, Gift gift, User user) {
        this.percentage = percentage;
        this.active = active;
        this.gift = gift;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", percentage=" + percentage +
                ", gift=" + gift +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(percentage, that.percentage) && Objects.equals(gift, that.gift) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentage, gift, user);
    }
}
