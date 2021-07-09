package ie.lukeandella.wedding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Entity(name = "User")
@Table(name = "users")
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
    private String password;
    
    //EMAIL
    @Column(name = "email", nullable = true)
    private String email;
    
    //VERIFICATION CODE - FOR EMAIL VERIFICATION OF REGISTRATION
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    
    //ENABLED
    private boolean enabled;
    
    //GIFTS
    @ManyToMany(mappedBy = "reservees") //"gifts" is the name of var Set<User> in class Gift
    private Set<Gift> gifts = new HashSet<>();


    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
    }

    //Return a boolean to verify that the gift has been removed.
    public boolean removeGift(Gift giftToRemove){
        for(Gift currentGift : gifts){
            if(currentGift.equals(giftToRemove)){
                gifts.remove(currentGift);
                return true;
            }
        }
        return false;
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
                ", password=" + password +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
