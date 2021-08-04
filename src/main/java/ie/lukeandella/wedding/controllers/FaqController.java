package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.pojos.Faq;
import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.services.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class FaqController {

    @Autowired
    FaqService faqService;

    @Autowired
    public FaqController(FaqService faqService){
        this.faqService = faqService;
    }

    @GetMapping("/faqs")
    public String showAllFaqs(Model model){
        model.addAttribute("faqs", faqService.getFaqs());
        model.addAttribute("faq_to_add_or_update", new Faq());
        return "faqs/faqs";
    }

    /*
        * Add an FAQ
     */
    @PostMapping("faqs/add")
    public String addFaq(@ModelAttribute("faq_to_add_or_update") Faq faq){
        faqService.addFaq(faq);
        return "faqs/faq-added";
    }

    /*
        * Update an FAQ
     */
    @PostMapping("/faqs/update")
    public String updateFaqInfo(@ModelAttribute("faq_to_add_or_update") Faq faqToUpdate, Model model){
        //Update the FAQ object with info provided by the admin
        faqService.updateFaq(faqToUpdate.getId(), faqToUpdate.getQuestion(), faqToUpdate.getAnswer());
        model.addAttribute("updated_faq", faqService.getFaqById(faqToUpdate.getId()));
        return "faqs/faq-updated";
    }

    /*
        * Delete an FAQ
     */
    @PostMapping("/faqs/delete/{id}")
    public String deleteFaq(@PathVariable("id") Long faqId, Model model){
        model.addAttribute("faq", faqService.getFaqById(faqId));
        faqService.deleteFaq(faqId);
        return "faqs/faq-deleted";
    }
}
