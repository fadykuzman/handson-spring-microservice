package learn.fady.microservices.core.review.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Transactional(readOnly = true)
    List<Review> findProductById(int productId);
}
