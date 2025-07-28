package in.sp.main.Service;

import in.sp.main.Entity.Feedback;
import in.sp.main.Repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceAdmin {

    @Autowired
    private FeedbackRepo feedbackRepo;

    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }

    public Feedback findById(Integer id) {
        return feedbackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with ID: " + id));
    }

    public Feedback updateFeedback(Integer id, Feedback feedback) {
        Feedback existing = findById(id);
        existing.setContent(feedback.getContent());
        return feedbackRepo.save(existing);
    }

    public void feedbackDelete(Integer id) {
        feedbackRepo.deleteById(id);
    }
}
