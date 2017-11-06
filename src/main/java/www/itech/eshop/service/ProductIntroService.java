package www.itech.eshop.service;

import www.itech.eshop.model.ProductIntro;

public interface ProductIntroService {
	
	public void add(ProductIntro productIntro, String operactionType);
	
	public void update(ProductIntro productIntro, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public ProductIntro findById(Long id);
	
}
