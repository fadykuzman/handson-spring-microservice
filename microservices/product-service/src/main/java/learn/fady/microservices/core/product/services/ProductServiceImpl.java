package learn.fady.microservices.core.product.services;

import learn.fady.api.core.product.Product;
import learn.fady.api.core.product.ProductService;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.exceptions.NotFoundException;
import learn.fady.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product getProduct(int id) {
        if (id < 1) throw new InvalidInputException("Invalid productId: " + id);
        if (id == 13) throw new NotFoundException("No Product found for productId: " + id);
        return new Product(id, "name-" + id, 123, serviceUtil.getServiceAddress());
    }
}
