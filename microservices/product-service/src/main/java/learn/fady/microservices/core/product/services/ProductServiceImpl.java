package learn.fady.microservices.core.product.services;

import learn.fady.api.core.product.Product;
import learn.fady.api.core.product.ProductService;
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
        return new Product(id, "name-" + id, 123, serviceUtil.getServiceAddress());
    }
}
