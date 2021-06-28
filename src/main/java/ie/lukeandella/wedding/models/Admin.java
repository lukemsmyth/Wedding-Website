package ie.lukeandella.wedding.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "Admin")
@Table(name = "admin")
public class Admin {

    //ID
    @Id
    @SequenceGenerator(name = "admin_sequence", sequenceName = "admin_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_sequence")
    @Column(name = "admin_id")
    private Long id;
    //USERNAME
    @Column(name = "username", nullable = false)
    private String username;
    //PASSWORD
    @Column(name = "password", nullable = false)
    private char[] password;

    public Admin(){}

    public Admin(String username, char[] password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}
