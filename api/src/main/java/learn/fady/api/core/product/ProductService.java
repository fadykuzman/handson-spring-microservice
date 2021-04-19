package learn.fady.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
