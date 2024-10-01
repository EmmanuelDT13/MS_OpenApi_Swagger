package com.emmanuel.swagger.api.controller;

import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import com.emmanuel.swagger.infraestructure.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@MockBean
	private IProductService IProductService;
	
	@Autowired
	private MockMvc mockProductController;
	
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void createObjectMapper() {
		this.objectMapper = new ObjectMapper(); 	
	}
	
	@Nested
	@DisplayName(value="This is the Get Family Nested Class")
	class getTestCases{
	
		@Test
		@Tag(value="Get Family")
		@DisplayName(value="Get all the products")
		void testGetAllProducts() throws Exception{
			//Given
			List<ProductDtoRequest> products = List.of(new ProductDtoRequest(1,"Laptop", 200), new ProductDtoRequest(2,"Ipad", 300));
			when(IProductService.getAllProducts()).thenReturn(products);
			
			//When
			mockProductController.perform(get("/products/getAll"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			//Then
					.andExpect(jsonPath("$[0].name").value("Laptop"))
					.andExpect(jsonPath("$[0].price").value(200))
					.andExpect(jsonPath("$[0].id").exists())
					.andExpect(jsonPath("$[0].proveedor").doesNotExist())
					.andExpect(jsonPath("$.length()").value(2))
					.andExpect(jsonPath("$").isArray());
			
			verify(IProductService, atMostOnce()).getAllProducts();
			verify(IProductService, atMost(1)).getAllProducts();
			verify(IProductService, atLeastOnce()).getAllProducts();
			verify(IProductService, atLeast(1)).getAllProducts();
			verify(IProductService, never()).getOneProduct(anyInt());
		}
		
		@Test
		@Tag(value="Get Family")
		@DisplayName(value="Get One Product")
		void testGetOneProduct() throws Exception{
			
			//Given
			when(IProductService.getOneProduct(1)).thenReturn(new ProductDtoRequest(1,"Laptop", 200));
			
			//When
			mockProductController.perform(get("/products/getOne/{idProduct}", 1))
			//Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Laptop"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.expiration").doesNotExist())
				.andExpect(jsonPath("$.price").exists());
			
			
			verify(IProductService, atLeastOnce()).getOneProduct(anyInt());
			verify(IProductService, atMostOnce()).getOneProduct(1);
			verify(IProductService, never()).getAllProducts();
		}
	}
	
	@Test
	void testCreateOneProduct() throws Exception {
		
		//Given
		ProductDtoRequest product = new ProductDtoRequest(100, "Cepillo de Dientes", 11);
		String productString = this.objectMapper.writeValueAsString(product);
		when(IProductService.createOneProduct(any(ProductDtoRequest.class))).thenReturn(product);
		
		//When
		mockProductController.perform(post("/products/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productString))
		//Then
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").isString())
				.andExpect(jsonPath("$.lastModifiedBy").doesNotExist())
				.andExpect(jsonPath("$.name").value("Cepillo de Dientes"));
		
		verify(IProductService, times(1)).createOneProduct(any(ProductDtoRequest.class));
		verify(IProductService, never()).deleteProduct(anyInt());
	}
}
