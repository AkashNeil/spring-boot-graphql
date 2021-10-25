package io.github.seebaware.GraphQL;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
class CustomerGraphqlController {

    private final CustomerRepository repository;

    public CustomerGraphqlController(CustomerRepository repository) {
        this.repository = repository;
    }

    // @SchemaMapping(typeName = "Query", field = "Customers")
    @QueryMapping
    Flux<Customer> customers () {
        return this.repository.findAll();
    }

}
