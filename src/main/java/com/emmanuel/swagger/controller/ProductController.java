package com.emmanuel.swagger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emmanuel.swagger.model.Client;
import com.emmanuel.swagger.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/products")
public class ProductController {

	@GetMapping(path="/getOne/{idProduct}")
	@Operation(summary="Get one product", description="This endpoint returns one product by its product id. The product most exist in the database.")
	@ApiResponses(value= {
		@ApiResponse(responseCode="200", description="If you get one 200, that means that we have found the Product."),
		@ApiResponse(responseCode="500", description="Something has failed in the server."),
		@ApiResponse(responseCode="404", description="The user doesn't exist.")
	})
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<Product> getProduct(@Parameter(description="You must specify the product id") @PathVariable Integer idProduct){
		Product product = new Product(idProduct, "Laptop", 2000);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@Operation(summary="Creates a new product.", description="Endpoint that creates one new product and saves it in the database.")
	@ApiResponse(responseCode="201", description="Indicates that the Product was saved succesfully in the database.")
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<Product> createProduct(@RequestBody(description="Please, create the payload with the follow atributes.") Product product){
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	@PutMapping("/modify")
	@Operation(summary="Creates one client.", description="Endpoint that creates one client in the database.")
	@ApiResponse(responseCode="201", description="Indicates that the Client was saved succesfully in the database.")
	@Tag(name="Clients", description="This category is responsible of the clients.")
	public ResponseEntity<Client> createClient(@RequestBody(description="Please, create the payload with the follow atributes.") Client client){
		return new ResponseEntity<>(client, HttpStatus.CREATED);
	}
	
}
