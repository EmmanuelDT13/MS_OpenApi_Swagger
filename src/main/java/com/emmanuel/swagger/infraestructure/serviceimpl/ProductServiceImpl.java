package com.emmanuel.swagger.infraestructure.serviceimpl;

import com.emmanuel.swagger.domain.model.Product;
import com.emmanuel.swagger.domain.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import com.emmanuel.swagger.infraestructure.service.ICacheService;
import com.emmanuel.swagger.infraestructure.service.IProductService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService, InitializingBean, ICacheService{

	@Autowired
	private ProductRepository ProductRepository;
	
	//When I get one product, I am saving it in the product cache.
	@Override
	@Cacheable(value="product", key = "#idProduct")
	public ProductDtoRequest getOneProduct(Integer idProduct) {
		Product product = ProductRepository.findById(idProduct).get();
		ProductDtoRequest productResponse = new ProductDtoRequest(product.getId(), product.getName(), product.getPrice()); 
		return productResponse;
	}

	//When I get the list of all the products, I am saving it in the cache called productList.
	@Override
	@Cacheable(value="productList")
	public List<ProductDtoRequest> getAllProducts() {
		List<Product> products = ProductRepository.findAll();
		List<ProductDtoRequest> productsResponse = products.stream().map(el -> {
			return new ProductDtoRequest(el.getId(), el.getName(), el.getPrice());
		}).collect(Collectors.toList());
		return productsResponse;
	}

	//If I create one new product, I am cleaning the cache productList, becausa I don't want to get the deprecated version of
	//the list that the cache has currently where there was no the new product that I have added.
	@Override
	@CacheEvict(value="productList", allEntries=true)
	public ProductDtoRequest createOneProduct(ProductDtoRequest product) {
		Product productToSave = new Product(product.getName(), product.getPrice());	
		ProductRepository.save(productToSave);
		product.setId(productToSave.getId());
		return product;
	}

	//If I update one product, I am goint to update its reference in cache, I don't need to remove it.
	//What I do remove is the productList again, because I want to have consistency.
	@Override
	@CachePut(value="product", key="#idProduct", condition="#idProduct != null")
	@CacheEvict(value="productList", allEntries=true)
	public ProductDtoRequest modifyProduct(ProductDtoRequest product, Integer idProduct) {
		Product productToModify = ProductRepository.findById(idProduct).get();
		productToModify.setName(product.getName());
		productToModify.setPrice(product.getPrice());
		product.setId(idProduct);
		return product;
	}
	
	//If I remove one element in the database, I am going to remove it in the cache as well.
	@Override
	@CacheEvict(value="product", key="#idProduct", condition="#idProduct != null")
	public void deleteProduct(Integer idProduct) {
		ProductRepository.deleteById(idProduct);
	}
	
	//By this method I am cleaning all the cache products.
	@Override
	@CacheEvict(value="products", allEntries=true)
	public void cleanCache() {}
	
	//This method is only for preloading the cache when the application starts. It's called in the CacheConfiguration class.
	@Override
	public void afterPropertiesSet() throws Exception {
		List<Product> products = Arrays.asList(new Product("Laptop", 2000), new Product("Desktop", 2300),
				new Product("Iphone", 4500), new Product("Bed", 9000), new Product("Sofa", 8200));
	ProductRepository.saveAll(products);
	}

}
