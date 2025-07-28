package in.sp.main.Service;

import in.sp.main.Entity.Feedback;
import in.sp.main.Entity.User;
import in.sp.main.Repository.FeedbackRepo;
import in.sp.main.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Feedback> getAllFeedbackByUser(String username) {
        User user = userRepo.findByUsername(username);
        return feedbackRepo.findByUser(user);
    }

    public Feedback getFeedbackById(Integer id, String username) {
        User user = userRepo.findByUsername(username);
        return feedbackRepo.findById(id)
                .filter(fb -> fb.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    public Feedback createFeedback(Feedback feedback, String username) {
        User user = userRepo.findByUsername(username);
        feedback.setUser(user);
        return feedbackRepo.save(feedback);
    }

    public Feedback updateFeedback(Integer id, Feedback feedback, String username) {
        Feedback existing = getFeedbackById(id, username);
        existing.setContent(feedback.getContent());
        return feedbackRepo.save(existing);
    }

    public void deleteFeedback(Integer id, String username) {
        Feedback feedback = getFeedbackById(id, username);
        feedbackRepo.delete(feedback);
    }

}
