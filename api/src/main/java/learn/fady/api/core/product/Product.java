package learn.fady.api.core.product;


public class Product {
    private final int id;
    private final String name;
    private final int weight;
    private final String serviceAddress;


    public Product(int id, String name, int weight, String serviceAddress) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.serviceAddress = serviceAddress;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }
}
