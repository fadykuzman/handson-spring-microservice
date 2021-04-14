package learn.fady.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {


    @GetMapping(
            value = "/product/{id}",
            produces = "application/json"
    )
    Product getProduct(@PathVariable int id);
}
