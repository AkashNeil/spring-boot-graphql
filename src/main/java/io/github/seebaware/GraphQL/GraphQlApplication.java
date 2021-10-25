package io.github.seebaware.GraphQL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@SpringBootApplication
public class GraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQlApplication.class, args);

		// Customer customer = new Customer(1, "Jack");


	}

}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}

record Customer (@JsonProperty("id") @Id Integer id, @JsonProperty("name") String name) {

}
