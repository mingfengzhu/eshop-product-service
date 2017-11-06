package www.itech.eshop.service;

import www.itech.eshop.model.ProductSpecification;

public interface ProductSpecificationService {
	
	public void add(ProductSpecification productSpecification, String operactionType);
	
	public void update(ProductSpecification productSpecification, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public ProductSpecification findById(Long id);
	
	public ProductSpecification findByProductId(Long productId);
	
}
