package learn.fady.microservices.core.recommendation.services;

import learn.fady.api.core.recommendation.Recommendation;
import learn.fady.api.core.recommendation.RecommendationService;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {
    ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
        return List.of(
                new Recommendation(1, productId, "tido", 4, "bla bla", serviceUtil.getServiceAddress())
        );
    }
}
