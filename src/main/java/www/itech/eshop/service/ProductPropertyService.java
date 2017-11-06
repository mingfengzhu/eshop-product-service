package www.itech.eshop.service;

import www.itech.eshop.model.ProductProperty;

public interface ProductPropertyService {
	
	public void add(ProductProperty productProperty, String operactionType);
	
	public void update(ProductProperty productProperty, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public ProductProperty findById(Long id);
	
	public ProductProperty findByProductId(Long productId);
	
}
