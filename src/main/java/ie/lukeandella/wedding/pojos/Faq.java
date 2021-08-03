package ie.lukeandella.wedding.pojos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "Faq")
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "faq_id")
    private Long id;

    @Column(name = "q")
    private String question;

    @Column(name = "a")
    private String answer;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Faq(){}

    public Faq(String question, String answer, LocalDateTime lastUpdated) {
        this.question = question;
        this.answer = answer;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String a) {
        this.answer = a;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
