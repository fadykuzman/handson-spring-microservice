package learn.fady.microservices.core.review.services;

import learn.fady.api.core.review.Review;
import learn.fady.api.core.review.ReviewService;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReviews(int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
        return List.of(
                new Review(1, productId, "posto", "trabala", "Chicko chiki", serviceUtil.getServiceAddress())
        );
    }
}
