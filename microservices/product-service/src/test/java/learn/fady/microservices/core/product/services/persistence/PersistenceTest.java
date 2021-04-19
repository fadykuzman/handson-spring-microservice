package learn.fady.microservices.core.product.services.persistence;

import com.mongodb.DuplicateKeyException;
import learn.fady.microservices.core.product.persistence.ProductEntity;
import learn.fady.microservices.core.product.persistence.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
public class PersistenceTest {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceTest.class);

    @Autowired
    private ProductRepository repository;
    private ProductEntity saved;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        ProductEntity productEntity = new ProductEntity(1, "n", 1);
        saved =  repository.save(productEntity);
        assertEqualsProduct(productEntity, saved);
    }

    @Test
    void create() {
        ProductEntity productEntity = new ProductEntity(2, "n", 2);
        saved = repository.save(productEntity);
        ProductEntity found = repository.findById(productEntity.getId()).get();
        assertEqualsProduct(productEntity, found);

        assertEquals(2, repository.count());
    }

    @Test
    void update() {
        saved.setName("n2");
        repository.save(saved);

        ProductEntity found = repository.findById(saved.getId()).get();
        assertEquals(1, (long) found.getVersion());
        assertEquals("n2", found.getName());
    }

    @Test
    void delete() {
        repository.delete(saved);
        assertFalse(repository.existsById(saved.getId()));
    }

    @Test
    void testGetByProductId() {
        Optional<ProductEntity> entity = repository.findByProductId(saved.getProductId());
        assertTrue(entity.isPresent());
        assertEqualsProduct(saved, entity.get());
    }

    @Test
    void duplicateError() {
        ProductEntity entity = new ProductEntity(saved.getProductId(), "n", 1);
        LOG.debug("expected: " + entity.toString());
        LOG.debug("actual: " + saved.toString());
        assertThrows(DuplicateKeyException.class, () -> repository.save(entity) );
    }

    private void assertEqualsProduct(ProductEntity expected, ProductEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getVersion(), actual.getVersion());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getName() ,actual.getName());
        assertEquals(expected.getWeight(), actual.getWeight());
    }
}
