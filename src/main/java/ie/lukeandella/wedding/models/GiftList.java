package ie.lukeandella.wedding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "GiftList")
@Table(name = "gift_list")
public class GiftList {

    //ID
    @Id
    @SequenceGenerator(name = "gift_list_sequence", sequenceName = "gift_list_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gift_list_sequence")
    @Column(name = "gift_list_id")
    private Long id;
    //GIFTS
    @JsonIgnore
    @OneToMany(mappedBy = "gift_list")
    private Set<Gift> gifts = new HashSet<>();

    public GiftList(){}

    public GiftList(Set<Gift> gifts){
        this.gifts = gifts;
    }

    public Set<Gift> getGifts(){
        return this.gifts;
    }

    public void setGifts(Set<Gift> gifts){
        this.gifts = gifts;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
    }

    @Override
    public String toString() {
        return "GiftList{" +
                "gifts=" + gifts +
                '}';
    }
}
