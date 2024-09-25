package com.emmanuel.swagger.infraestructure.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import com.emmanuel.swagger.domain.model.Product;
import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import com.emmanuel.swagger.domain.repository.ProductRepository;
import com.emmanuel.swagger.infraestructure.service.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)		//Indicates that we are going to create only one class instance for all the test cases.
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)	//Indicates that we are going to create one instance of the class for wach test case.
class ProductServiceImplTest {

	private TestInfo testInfo;
	
	private TestReporter testReporter;
	
	private ProductDtoRequest productFromDb;
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	@BeforeAll
	static void initTestCases() {
		System.out.println("The test cases are about to begin...");
	}
	
	@AfterAll
	static void finishTestCases() {
		System.out.println("The test cases have finished.");

	}
	
	@BeforeEach
	void instanciateProductFromDb(TestInfo testInfo, TestReporter testReporter) {
		System.out.println("Starting test...");
		productFromDb = new ProductDtoRequest(1, "Laptop", 200);
		this.testInfo = testInfo;
		this.testReporter = testReporter;
		MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void endingTheTest() {
		System.out.println("The test has finished.");
	}
	
	
	
	
	@Nested
	@DisplayName("Class that groups all the product test cases")
	class getProducts{
		
		@Test
		@DisplayName("Testing Get One Product")
		@EnabledOnOs(OS.WINDOWS)
		@Tag("Product")
		void testGetOneProduct() {
			
			System.out.println("This is the test info of " + testInfo.getTestMethod().toString() + ": " + testInfo) ;
			
			ProductDtoRequest product = new ProductDtoRequest(1, "Laptop", 200);
			String expectedNameProduct = product.getName();
			Integer expectedPriceProduct = product.getPrice();
			
			//Vamos a simular que este es el producto que hemos extraído de la base de datos (solo por el momento).
			//Lo comenté porque vamos a estar creando esta variable desde el @BeforeEach, así no estamos generando código repetitivo.
			//ProductDtoRequest productFromDb = new ProductDtoRequest(1, "Laptop", 200);
			
			Assertions.assertAll(
				()-> Assertions.assertEquals(expectedNameProduct, productFromDb.getName(), ()->"The harcoded product name must be equals than the database one"),
				()-> Assertions.assertFalse(!expectedPriceProduct.equals(productFromDb.getPrice()), ()->"The harcoded product price mustn't be different than the database one"),
				()-> Assertions.assertTrue(expectedPriceProduct.equals(productFromDb.getPrice()), ()->"The harcoded product price must be equals than the database one"),
				()-> Assertions.assertInstanceOf(String.class, expectedNameProduct, ()->"The harcoded product name must be one instance os String"),
				()-> Assertions.assertNotSame(expectedPriceProduct, productFromDb.getId(), ()->"The price is not equal than the id")
			);
		

		}
		
	}
	
	
	
	@Test
	@DisplayName("Test to modify the product name")
	@DisabledOnOs(OS.LINUX)
	@EnabledOnJre(JRE.JAVA_17)
	void testModifyProduct() {
		
		System.out.println("This is the test info of " + testInfo.getTestMethod().toString() + ": " + testInfo) ;
		
		ProductDtoRequest product = new ProductDtoRequest(1, "SmarthPhone", 200);
		String newName = product.getName();
		
		//Lo comenté porque vamos a estar creando esta variable desde el @BeforeEach, así no estamos generando código repetitivo.
		//ProductDtoRequest productFromDb = new ProductDtoRequest(1, "Laptop", 200);
		
		productFromDb.setName(newName);
		
		Assertions.assertNotNull(newName);
		Assertions.assertEquals(newName, productFromDb.getName());
		
	}

	@Test
	@EnabledIfSystemProperty(named="user.name", matches="emman") 
	@EnabledIfEnvironmentVariable(named="OS", matches="Windows_NT")
	@Timeout(value=5000, unit=TimeUnit.MILLISECONDS)
	void testGetAllProducts() {
		
		//System.getProperties().forEach((key, value)->{System.out.println("This is the key: " + key + ": " + value);});
		
		Assumptions.assumeTrue(System.getProperties().containsKey("user.name"));

		
		boolean[] nucleosValid = new boolean[] {false};
		System.getenv().forEach((key,value) ->{
			if(key.equals("NUMBER_OF_PROCESSORS") && Integer.parseInt(value) > 3) nucleosValid[0] = true;
		});
		Assumptions.assumingThat(nucleosValid[0], () ->{
			Assertions.assertTrue(2 > 1);
		});
	
		System.getenv().forEach((key,value) -> System.out.println(key + ": " + value));
		
	}

	@Test
	void testFilterOneProduct() {
		
		Product product1 = new Product(1,"Laptop", 2000);
		Product product2 = new Product(1,"PC", 2500);
		List<Product> products = List.of(product1, product2);
		
		when(productRepository.findAll()).thenReturn(products);
		
		
		//List<Product> productsFromDb = productServiceImpl.getAllProducts().stream().map(el->new Product(el.getId(),el.getName(),el.getPrice())).toList();
		Product lap = productServiceImpl.findOneByList("Laptop").orElseThrow();
		
		Assertions.assertEquals(product1.getName(), lap.getName());
		
	}

	@Test
	@Disabled
	void testDeleteProduct() {
		Assertions.assertThrows(NullPointerException.class, () ->productFromDb.toString());
	}

}
