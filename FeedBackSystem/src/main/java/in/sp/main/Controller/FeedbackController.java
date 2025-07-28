package in.sp.main.Controller;

import in.sp.main.Entity.Feedback;
import in.sp.main.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/show")
    public String getAllFeedback(Model model, Principal principal){
        String username = principal.getName();
        List<Feedback> feedback = feedbackService.getAllFeedbackByUser(username);
        model.addAttribute("feedback1",feedback);
        model.addAttribute("username", username);
        return "feedback";
    }

    @GetMapping("/add")
    public String createUser(Model model){

        Feedback feedback = new Feedback();
        model.addAttribute("feedback2",feedback);
        return "add_feed";

    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("feedback2") Feedback feedback,Principal principal){
        String username = principal.getName();
        Feedback feedback1 = feedbackService.createFeedback(feedback,username);
        return "redirect:/show";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id,Principal principal,Model model){
        String username = principal.getName();
        Feedback feedback = feedbackService.getFeedbackById(id,username);
        model.addAttribute("feedback3",feedback);
        return "edit_feed";
}

    @PostMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Integer id,@ModelAttribute("feedback3") Feedback feedback, Principal principal){
        String username = principal.getName();
        Feedback feedback5 = feedbackService.updateFeedback(id,feedback,username);
        return "redirect:/show";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Principal principal) {
        String username = principal.getName();
        feedbackService.deleteFeedback(id, username);
        return "redirect:/show";
    }
}
