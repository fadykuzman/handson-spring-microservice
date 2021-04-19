package learn.fady.api.core.review;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewService {

    @GetMapping(
            value = "/review",
            produces = "application/json"
    )
    List<Review> getReviews(@RequestParam(value = "productId", required = true) int productId);

    @PostMapping(
            value = "/review",
            consumes = "application/api",
            produces = "application/api"
    )
    Review createReview(@RequestBody Review body);

    @DeleteMapping(value = "/review/{productId}")
    void deleteReviews(@PathVariable int productId);
}
