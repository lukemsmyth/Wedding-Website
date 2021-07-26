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

    //RESERVATIONS
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<>();

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", enabled=" + enabled +
                ", reservations=" + reservations +
                '}';
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

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
