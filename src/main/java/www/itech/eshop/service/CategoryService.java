package www.itech.eshop.service;

import www.itech.eshop.model.Category;

public interface CategoryService {
	
	public void add(Category category, String operactionType);
	
	public void update(Category category, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public Category findById(Long id);
	
}
