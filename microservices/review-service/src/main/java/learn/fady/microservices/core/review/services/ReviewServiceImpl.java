package learn.fady.microservices.core.review.services;

import learn.fady.api.core.review.Review;
import learn.fady.api.core.review.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    @Override
    public List<Review> getReviews(int productId) {
        return List.of(
                new Review(1, productId, "posto", "trabala", "Chicko chiki")
        );
    }
}
