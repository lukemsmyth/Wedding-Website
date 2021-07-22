package ie.lukeandella.wedding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Entity(name = "User")
@Table(name = "users")
public class User {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    
    //USERNAME
    @Column(name = "name", nullable = false)
    private String name;
   
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
    //mappedBy = "groupMemberList" makes the Group entity the owning entity and so only changes to that entity are persisted. //https://stackoverflow.com/questions/52203892/updating-manytomany-relationships-in-jpa-or-hibernate
    @ManyToMany(mappedBy = "reservees", fetch = FetchType.EAGER) //"gifts" is the name of var Set<User> in class Gift
    @JsonIgnore
    private Set<Gift> gifts = new HashSet<>();

    public User(){}

    public User(String name, String password) {
        this.name = name.toLowerCase(); //name does not need to be case sensitive
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.name = name.toLowerCase();
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
                ", username='" + name + '\'' +
                ", password=" + password +
//                ", gifts=" + gifts +
                '}';
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
