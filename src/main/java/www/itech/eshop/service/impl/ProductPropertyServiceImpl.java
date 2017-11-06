package www.itech.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.itech.eshop.mapper.ProductPropertyMapper;
import www.itech.eshop.model.ProductProperty;
import www.itech.eshop.rabbitmq.RabbitMQSender;
import www.itech.eshop.rabbitmq.RabbitQueue;
import www.itech.eshop.service.ProductPropertyService;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductProperty productProperty, String operactionType) {
		productPropertyMapper.add(productProperty); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void update(ProductProperty productProperty, String operactionType) {
		productPropertyMapper.update(productProperty); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void delete(Long id, String operactionType) {
		ProductProperty productProperty = findById(id);
		productPropertyMapper.delete(id); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product_property\", \"id\": " + id + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public ProductProperty findById(Long id) {
		return productPropertyMapper.findById(id);
	}

	@Override
	public ProductProperty findByProductId(Long productId) {
		return productPropertyMapper.findByProductId(productId);
	}

}
