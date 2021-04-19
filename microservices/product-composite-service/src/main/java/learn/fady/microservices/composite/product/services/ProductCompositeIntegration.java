package learn.fady.microservices.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.fady.api.composite.RecommendationSummary;
import learn.fady.api.core.product.Product;
import learn.fady.api.core.product.ProductService;
import learn.fady.api.core.recommendation.Recommendation;
import learn.fady.api.core.recommendation.RecommendationService;
import learn.fady.api.core.review.Review;
import learn.fady.api.core.review.ReviewService;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.exceptions.NotFoundException;
import learn.fady.util.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort,

            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") String recommendationServicePort,

            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") String reviewServicePort

    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        productServiceUrl = String.format("http://%s:%s/product/", productServiceHost, productServicePort);
        recommendationServiceUrl = String.format("http://%s:%s/recommendation?productId=", recommendationServiceHost, recommendationServicePort);
        reviewServiceUrl = String.format("http://%s:%s/review?productId=", reviewServiceHost, reviewServicePort);
    }
    @Override
    public Product getProduct(int id) {
        try {
            String url = productServiceUrl + id;
            LOG.debug("Will call getProduct API on URL: {}", url);

            Product product = restTemplate.getForObject(url, Product.class);
            LOG.debug("Found a product with id: {}", product.getId());

            return product;
        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(e));

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(e));

                default:
                    LOG.warn("Got an unexpected HTTP error: {}, will throw it", e.getStatusCode());
                    LOG.warn("Error body: {}", e.getResponseBodyAsString());
                    throw e;
            }
        }
    }

    @Override
    public Product createProduct(Product body) {
        return null;
    }

    @Override
    public void deleteProduct(int id) {

    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        try {
            String url = recommendationServiceUrl + productId;
            LOG.debug("Will call getRecommendations API on URL: {}", url);

            List<Recommendation> recommendations = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {
            }).getBody();
            LOG.debug("Found {} recommendation for a product with id: {}", recommendations.size(), productId);

            return recommendations;
        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Recommendation createRecommendation(Recommendation body) {
        return null;
    }

    @Override
    public void deleteRecommendations(int productId) {

    }

    @Override
    public List<Review> getReviews(int productId) {
        try {
            String url = reviewServiceUrl + productId;
            LOG.debug("Will call getReviews API on URL: {}", url);

            List<Review> reviews = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Review>>() {
                    }).getBody();
            LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);

            return reviews;
        } catch (Exception e) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Review createReview(Review body) {
        return null;
    }

    @Override
    public void deleteReviews(int productId) {

    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
