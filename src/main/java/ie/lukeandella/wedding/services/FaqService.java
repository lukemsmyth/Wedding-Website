package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.pojos.Faq;
import ie.lukeandella.wedding.repositories.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

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

    public void addFaq(Faq faq){
        faq.setLastUpdated(LocalDateTime.now());
        faqRepository.save(faq);
    }

    public void deleteFaq(Long faqId){
        faqRepository.deleteById(faqId);
    }

    @Transactional
    public void updateFaq(Long faqId, String question, String answer){
        Faq faq = initFaqObj(faqId);
        if(!question.isEmpty()){
            faq.setQuestion(question);
        }else{
            faq.setQuestion(faq.getQuestion());
        }
        if(!answer.isEmpty()){
            faq.setAnswer(answer);
        }else{
            faq.setAnswer(faq.getAnswer());
        }
        faq.setLastUpdated(LocalDateTime.now());
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
