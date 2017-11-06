package www.itech.eshop.service;

import www.itech.eshop.model.Product;

public interface ProductService {
	
	public void add(Product product, String operactionType);
	
	public void update(Product product, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public Product findById(Long id);
	
}
