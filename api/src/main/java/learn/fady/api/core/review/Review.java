package learn.fady.api.core.review;

public class Review {
    private final int id;
    private final int productId;
    private final String author;
    private final String subject;
    private final String content;
    private final String serviceAddress;


    public Review(int id, int productId, String author, String subject, String content, String serviceAddress) {
        this.id = id;
        this.productId = productId;
        this.author = author;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }
}
