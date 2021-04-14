package learn.fady.api.core.recommendation;

public class Recommendation {
    private final int id;
    private final int productId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;

    public Recommendation(int id, int productId, String author, int rate, String content, String serviceAddress) {
        this.id = id;
        this.productId = productId;
        this.author = author;
        this.rate = rate;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getAuthor() {
        return author;
    }

    public int getRate() {
        return rate;
    }

    public String getContent() {
        return content;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }
}
