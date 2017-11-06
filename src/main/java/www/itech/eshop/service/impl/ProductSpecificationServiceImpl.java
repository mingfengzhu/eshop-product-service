package www.itech.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.itech.eshop.mapper.ProductSpecificationMapper;
import www.itech.eshop.model.ProductSpecification;
import www.itech.eshop.rabbitmq.RabbitMQSender;
import www.itech.eshop.rabbitmq.RabbitQueue;
import www.itech.eshop.service.ProductSpecificationService;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductSpecification productSpecification, String operactionType) {
		productSpecificationMapper.add(productSpecification); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId() + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public void update(ProductSpecification productSpecification, String operactionType) {
		productSpecificationMapper.update(productSpecification); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId() + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public void delete(Long id, String operactionType) {
		ProductSpecification productSpecification = findById(id);
		productSpecificationMapper.delete(id); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product_specification\", \"id\": " + id + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public ProductSpecification findById(Long id) {
		return productSpecificationMapper.findById(id);
	}

	@Override
	public ProductSpecification findByProductId(Long productId) {
		return productSpecificationMapper.findByProductId(productId);
	}

}
