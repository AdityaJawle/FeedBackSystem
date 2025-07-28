package in.sp.main.Controller;

import in.sp.main.Entity.Feedback;
import in.sp.main.Entity.User;
import in.sp.main.Repository.FeedbackRepo;
import in.sp.main.Repository.UserRepo;
import in.sp.main.Service.FeedbackService;
import in.sp.main.Service.FeedbackServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private FeedbackServiceAdmin feedbackServiceAdmin;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());

        if (user.getRoles().contains("ROLE_ADMIN")) {
            List<Feedback> all = feedbackRepo.findAll();
            model.addAttribute("feed1", all);
        } else {
            List<Feedback> userFeedbacks = feedbackRepo.findByUser(user);
            model.addAttribute("feed1", userFeedbacks);
        }

        return "AdminFeed";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editadmin/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("feed2", feedbackServiceAdmin.findById(id));
        return "admin_edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/{id}")
    public String adminUpdateForm(@PathVariable("id") Integer id, @ModelAttribute("feed2") Feedback feedback) {
        feedbackServiceAdmin.updateFeedback(id, feedback);
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        feedbackServiceAdmin.feedbackDelete(id);
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/view/{id}")
    public String viewId(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("feed8", feedbackServiceAdmin.findById(id));
        return "view";
    }
}
