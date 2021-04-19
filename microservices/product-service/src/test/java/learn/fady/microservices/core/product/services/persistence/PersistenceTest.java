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
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;


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

    @Test
    void optimisticLockError() {
        ProductEntity entity1 = repository.findById(saved.getId()).get();
        ProductEntity entity2 = repository.findById(saved.getId()).get();

        entity1.setName("n1");
        repository.save(entity1);

        try {
            entity2.setName("n2");
            repository.save(entity2);
            fail("Expected an OptimisticLockingFailureException");
        } catch (OptimisticLockingFailureException e) { }

        ProductEntity updatedEntity = repository.findById(saved.getId()).get();
        assertEquals(1, (int) updatedEntity.getVersion());
        assertEquals("n1", updatedEntity.getName());
    }

    @Test
    void testPaging() {
        repository.deleteAll();
        List<ProductEntity> newProducts = rangeClosed(1001, 1010)
                .mapToObj(i -> new ProductEntity(i, "name " + i, i))
                .collect(Collectors.toList());
        repository.saveAll(newProducts);

        Pageable nextPage = PageRequest.of(0, 4, ASC, "productId");
        nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]", true);
        nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]", true);
        nextPage = testNextPage(nextPage, "[1009, 1010]", false);
    }

    private Pageable testNextPage(Pageable nextPage, String expectedProductIds, boolean expectedNextPage) {
        Page<ProductEntity> productPage = repository.findAll(nextPage);
        assertEquals(expectedProductIds,
                productPage.getContent().stream()
                .map(p -> p.getProductId())
                .collect(Collectors.toList())
                .toString()
        );
        assertEquals(expectedNextPage, productPage.hasNext());
        return productPage.nextPageable();
    }

    private void assertEqualsProduct(ProductEntity expected, ProductEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getVersion(), actual.getVersion());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getName() ,actual.getName());
        assertEquals(expected.getWeight(), actual.getWeight());
    }
}
