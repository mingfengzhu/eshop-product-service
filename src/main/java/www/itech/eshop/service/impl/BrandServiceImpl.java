package www.itech.eshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.itech.eshop.mapper.BrandMapper;
import www.itech.eshop.model.Brand;
import www.itech.eshop.rabbitmq.RabbitMQSender;
import www.itech.eshop.rabbitmq.RabbitQueue;
import www.itech.eshop.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private RabbitMQSender rabbitMQSender;

	public void add(Brand brand, String operactionType) {
		brandMapper.add(brand);
		String queue = null;

		if (operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if ("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if ("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue,
				"{\"event_type\": \"add\", \"data_type\": \"brand\", \"id\": " + brand.getId() + "}");
	}

	public void update(Brand brand, String operactionType) {
		brandMapper.update(brand);
		String queue = null;

		if (operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if ("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if ("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue,
				"{\"event_type\": \"update\", \"data_type\": \"brand\", \"id\": " + brand.getId() + "}");
	}

	public void delete(Long id, String operactionType) {
		brandMapper.delete(id);
		String queue = null;

		if (operactionType == null || "".equals(operactionType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if ("refresh".equals(operactionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if ("high".equals(operactionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		rabbitMQSender.send(queue,
				"{\"event_type\": \"delete\", \"data_type\": \"brand\", \"id\": " + id + "}");
	}

	public Brand findById(Long id) {
		return brandMapper.findById(id);
	}

	@Override
	public List<Brand> findByIds(String ids) {
		return brandMapper.findByIds(ids);
	}
}
