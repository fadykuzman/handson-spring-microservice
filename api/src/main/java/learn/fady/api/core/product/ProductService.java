package learn.fady.api.core.product;

import org.springframework.web.bind.annotation.*;

public interface ProductService {


    @GetMapping(
            value = "/product/{id}",
            produces = "application/json"
    )
    Product getProduct(@PathVariable int id);

    @PostMapping(
            value = "/product",
            consumes = "application/json",
            produces = "application/json"
    )
    Product createProduct(@RequestBody Product body);

    @DeleteMapping(value = "/product/{id}")
    void deleteProduct(@PathVariable int id);
}
