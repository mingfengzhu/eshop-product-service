package www.itech.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.itech.eshop.mapper.CategoryMapper;
import www.itech.eshop.model.Category;
import www.itech.eshop.rabbitmq.RabbitMQSender;
import www.itech.eshop.rabbitmq.RabbitQueue;
import www.itech.eshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(Category category, String operactionType) {
		categoryMapper.add(category); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"category\", \"id\": " + category.getId() + "}");
	}

	public void update(Category category, String operactionType) {
		categoryMapper.update(category); 
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"category\", \"id\": " + category.getId() + "}");
	}

	public void delete(Long id, String operactionType) {
		categoryMapper.delete(id);
		String queue = null;
		
		if(operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"category\", \"id\": " + id + "}");
	}

	public Category findById(Long id) {
		return categoryMapper.findById(id);
	}

}
