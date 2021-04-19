package learn.fady.microservices.core.product.services;

import com.mongodb.DuplicateKeyException;
import learn.fady.api.core.product.Product;
import learn.fady.api.core.product.ProductService;
import learn.fady.microservices.core.product.persistence.ProductEntity;
import learn.fady.microservices.core.product.persistence.ProductRepository;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.exceptions.NotFoundException;
import learn.fady.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil, ProductRepository repository, ProductMapper mapper) {
        this.serviceUtil = serviceUtil;
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Product getProduct(int id) {
        if (id < 1) throw new InvalidInputException("Invalid productId: " + id);
        ProductEntity entity = repository.findByProductId(id)
                .orElseThrow(() -> new NotFoundException("No product found for Id: " + id));
        Product response = mapper.entityToApi(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());
        return response;
    }

    @Override
    public Product createProduct(Product body) {
        try {
            ProductEntity entity = mapper.apiToEntity(body);
            ProductEntity newEntity = repository.save(entity);
            return mapper.entityToApi(newEntity);
        } catch (DuplicateKeyException e) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getId());
        }
    }

    @Override
    public void deleteProduct(int id) {
        repository.findByProductId(id)
                .ifPresent(
                        repository::delete
                );
    }
}