package spring_mq.producer.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import spring_mq.producer.MqProducer;

public class MqProducerImpl implements MqProducer {

	@Autowired
	private AmqpTemplate amqptemplate;
	
	
	
	public void sendDataToQueue(String queueKey, Object object) {
		amqptemplate.convertAndSend(queueKey, object);

	}

}
