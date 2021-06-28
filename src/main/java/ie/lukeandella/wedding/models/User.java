package ie.lukeandella.wedding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Entity(name = "User")
@Table(name = "user")
public class User {

    //ID
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "user_id")
    private Long id;
    //USERNAME
    @Column(name = "username", nullable = false)
    private String username;
    //PASSWORD
    @Column(name = "password", nullable = false)
    private char[] password;
    //EMAIL
    @Column(name = "email", nullable = true)
    private String email;
    //GIFTS
    @JsonIgnore
    @OneToMany(mappedBy = "publisher")
    private Set<Gift> gifts = new HashSet<>();

    public User(){}

    public User(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, char[] password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
    }

    public Double getAmountCommitted(){
        Double total = 0.0;
        for(Gift gift : gifts){
            total += gift.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", gifts=" + gifts +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }

}
