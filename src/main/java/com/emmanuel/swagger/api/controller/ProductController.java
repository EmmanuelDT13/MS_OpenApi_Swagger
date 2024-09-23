package com.emmanuel.swagger.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.emmanuel.swagger.infraestructure.service.IProductService;

@RestController
@RequestMapping(path="/products")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	@GetMapping(path="/getOne/{idProduct}")
	@Operation(summary="Get one product", description="This endpoint returns one product by its product id. The product most exist in the database.")
	@ApiResponses(value= {
		@ApiResponse(responseCode="200", description="If you get one 200, that means that we have found the Product."),
		@ApiResponse(responseCode="500", description="Something has failed in the server."),
		@ApiResponse(responseCode="404", description="The user doesn't exist.")
	})
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<ProductDtoRequest> getProduct(@Parameter(description="You must specify the product id") @PathVariable Integer idProduct){
		ProductDtoRequest productResponse = productService.getOneProduct(idProduct);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
	
	
	@GetMapping(path="/getAll")
	@Operation(summary="Get one product", description="This endpoint returns one product by its product id. The product most exist in the database.")
	@ApiResponses(value= {
		@ApiResponse(responseCode="200", description="If you get one 200, that means that we have found the Product."),
		@ApiResponse(responseCode="500", description="Something has failed in the server."),
		@ApiResponse(responseCode="404", description="The user doesn't exist.")
	})
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<List<ProductDtoRequest>> getAllProducts(){
		List<ProductDtoRequest> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	@Operation(summary="Creates a new product.", description="Endpoint that creates one new product and saves it in the database.")
	@ApiResponse(responseCode="201", description="Indicates that the Product was saved succesfully in the database.")
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<ProductDtoRequest> createProduct(@RequestBody ProductDtoRequest product){
		System.out.println(product);
		ProductDtoRequest productResponse = productService.createOneProduct(product);
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/modify/{idProduct}")
	@Operation(summary="Modifies product.", description="Endpoint that modifies one existing product and saves it in the database.")
	@ApiResponse(responseCode="201", description="Indicates that the Product was modified succesfully in the database.")
	@Tag(name="Products", description="This category is responsible for the operations realted to products.")
	public ResponseEntity<ProductDtoRequest> editProduct(@RequestBody ProductDtoRequest product,@Parameter(description="It's necesary to define the id product that you want to update.") @PathVariable Integer idProduct){
		ProductDtoRequest productResponse = productService.modifyProduct(product, idProduct);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
	
	
	@Operation(summary="Deletes the product", description="By this method we remove one element from the database. We need to place the idProduct.")
	@DeleteMapping(path="delete/{idProduct}")
	@ApiResponse(responseCode="200", description="The element has been successfully removed from the database.")
	@Tag(name="Products", description="This section is used for the operations of the products.")
	public ResponseEntity<Void> deleteOneProduct(@Parameter(name="IdProduct",description="It's used for getting the product and deleting it.")  @PathVariable Integer idProduct){
		productService.deleteProduct(idProduct);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
