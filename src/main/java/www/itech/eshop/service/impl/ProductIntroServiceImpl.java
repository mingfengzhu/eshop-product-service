package www.itech.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.itech.eshop.mapper.ProductIntroMapper;
import www.itech.eshop.model.ProductIntro;
import www.itech.eshop.rabbitmq.RabbitMQSender;
import www.itech.eshop.rabbitmq.RabbitQueue;
import www.itech.eshop.service.ProductIntroService;

@Service
public class ProductIntroServiceImpl implements ProductIntroService {

	@Autowired
	private ProductIntroMapper productIntroMapper;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductIntro productIntro, String operactionType) {
		productIntroMapper.add(productIntro); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void update(ProductIntro productIntro, String operactionType) {
		productIntroMapper.update(productIntro); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void delete(Long id, String operactionType) {
		ProductIntro productIntro = findById(id);
		productIntroMapper.delete(id); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product_intro\", \"id\": " + id + ", \"product_id\": " + productIntro.getProductId() + "}");
	}
	
	public ProductIntro findById(Long id) {
		return productIntroMapper.findById(id);
	}

}
