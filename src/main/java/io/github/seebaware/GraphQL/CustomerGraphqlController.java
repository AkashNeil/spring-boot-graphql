package io.github.seebaware.GraphQL;

import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

@Controller
class CustomerGraphqlController {

    private final CustomerRepository repository;

    public CustomerGraphqlController(CustomerRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    Flux<Customer> customersByName(@Argument String name) {
        return this.repository.findByName(name);
    }

    @SchemaMapping(typeName = "Customer")
    Flux<Order> orders (Customer customer) {
        var orders = new ArrayList<Order>();
        for (var orderId = 1; orderId <= (Math.random() * 100); orderId++) {
            orders.add(new Order(orderId, customer.id()));
        }
        return Flux.fromIterable(orders);
    }

    @QueryMapping
    Flux<Customer> customers () {
        return this.repository.findAll();
    }

    @MutationMapping
    Mono <Customer> addCustomer(@Argument String name) {
        return this.repository.save(new Customer(null, name));
    }

    @SubscriptionMapping
    Flux <CustomerEvent> customerEvents (@Argument Integer customerId) {
        return this.repository.findById(customerId)
            .flatMapMany(customer -> {

                var stream = Stream.generate(
                        () -> new CustomerEvent(customer, Math.random() > .5 ? CustomerEventType.DELETED : CustomerEventType.UPDATED)
                );

                return Flux.fromStream(stream);

            })
            .delayElements(Duration.ofSeconds(1))
            .take(10);
    }

}
