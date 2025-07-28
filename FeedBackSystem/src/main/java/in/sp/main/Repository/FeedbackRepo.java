package in.sp.main.Repository;

import in.sp.main.Entity.Feedback;
import in.sp.main.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {

    List<Feedback> findByUser(User user);
}
