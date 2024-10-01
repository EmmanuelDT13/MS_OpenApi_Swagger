package com.emmanuel.swagger.api.controller;

import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import com.emmanuel.swagger.infraestructure.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
	
	@Test
	void testGetProduct() throws Exception{
		//Given
		List<ProductDtoRequest> products = List.of(new ProductDtoRequest(1,"Laptop", 200), new ProductDtoRequest(2,"Ipad", 300));
		when(IProductService.getAllProducts()).thenReturn(products);
		
		mockProductController.perform(get("/products/getAll"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value("Laptop"))
				.andExpect(jsonPath("$[0].price").value(200));
		
		verify(IProductService, atMostOnce()).getAllProducts();
		verify(IProductService, atMost(1)).getAllProducts();
		verify(IProductService, atLeastOnce()).getAllProducts();
		verify(IProductService, atLeast(1)).getAllProducts();
		verify(IProductService, never()).getOneProduct(anyInt());
	}
	
	@Test
	void testOneProduct() throws Exception{
		
		when(IProductService.getOneProduct(1)).thenReturn(new ProductDtoRequest(1,"Laptop", 200));
		
		mockProductController.perform(get("/products/getOne/1")).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Laptop"));
	}
	
	
	@Test
	void createOneProduct() throws Exception {
		
		ProductDtoRequest product = new ProductDtoRequest(100,"Cepillo de Dientes", 11);
		
		String roductString = objectMapper.writeValueAsString(product);
		
		mockProductController.perform(post("/products/create").contentType(MediaType.APPLICATION_JSON)
				.content(roductString))
		.andExpect(status().isCreated());
	}
}
