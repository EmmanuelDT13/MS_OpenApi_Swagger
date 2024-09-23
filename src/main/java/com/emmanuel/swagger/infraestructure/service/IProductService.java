package com.emmanuel.swagger.infraestructure.service;
import java.util.List;
import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;

public interface IProductService {

	public ProductDtoRequest getOneProduct(Integer idProduct);
	
	public List<ProductDtoRequest> getAllProducts();
	
	public ProductDtoRequest createOneProduct(ProductDtoRequest product);
	
	public ProductDtoRequest modifyProduct(ProductDtoRequest product, Integer idProduct);
	
	public void deleteProduct(Integer idProduct);
	
}
