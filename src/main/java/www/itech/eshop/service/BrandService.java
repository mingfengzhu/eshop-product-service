package www.itech.eshop.service;

import java.util.List;

import www.itech.eshop.model.Brand;

public interface BrandService {
	
	public void add(Brand brand, String operactionType);
	
	public void update(Brand brand, String operactionType);
	
	public void delete(Long id, String operactionType);
	
	public Brand findById(Long id);
	
	public List<Brand> findByIds(String ids);
	
}
