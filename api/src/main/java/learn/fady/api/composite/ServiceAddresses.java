package learn.fady.api.composite;

public class ServiceAddresses {
    private final String composite;
    private final String product;
    private final String review;
    private final String recommendation;

    public ServiceAddresses(String composite, String product, String review, String recommendation) {
        this.composite = composite;
        this.product = product;
        this.review = review;
        this.recommendation = recommendation;
    }

    public String getComposite() {
        return composite;
    }

    public String getProduct() {
        return product;
    }

    public String getReview() {
        return review;
    }

    public String getRecommendation() {
        return recommendation;
    }
}
