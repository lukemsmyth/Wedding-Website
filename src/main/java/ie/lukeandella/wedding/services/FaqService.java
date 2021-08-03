package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.pojos.Faq;
import ie.lukeandella.wedding.repositories.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FaqService {

    @Autowired
    FaqRepository faqRepository;

    @Autowired
    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<Faq> getFaqs(){
        return faqRepository.findAll();
    }

    public void addFaq(String question, String answer, LocalDateTime lastUpdated){
        faqRepository.save(new Faq(question, answer, lastUpdated));
    }

    public void deleteFaq(Long faqId){
        faqRepository.deleteById(faqId);
    }

    @Transactional
    public void updateFaq(Long faqId, String question, String answer, LocalDateTime lastUpdated){
        Faq faq = initFaqObj(faqId);
        if(answer != null){
            faq.setAnswer(answer);
        }else{
            faq.setAnswer(faq.getAnswer());
        }
        if(question != null){
            faq.setQuestion(question);
        }else{
            faq.setQuestion(faq.getQuestion());
        }
        faq.setLastUpdated(lastUpdated);
    }

    public Faq initFaqObj(Long faqId){
        return faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalStateException
                                ("Faq with ID: " + faqId + " does not exist."));
    }

    public Faq getFaqById(Long faqId) {
        return initFaqObj(faqId);
    }
}
