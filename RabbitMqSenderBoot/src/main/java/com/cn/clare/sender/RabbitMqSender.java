package com.cn.clare.sender;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.context.support.StaticApplicationContext;

import com.cn.clare.util.SpringBeanUtil;

public class RabbitMqSender {

	private static RabbitMqSender rabbitMqSender;
	
	public RabbitMqSender() {
		
	}
	
	public static RabbitMqSender InitMqSender() {
		if(rabbitMqSender==null) {
			final RabbitTemplate rabbitTemplate = SpringBeanUtil.getBeans(RabbitTemplate.class) ;
			rabbitTemplate.setReturnCallback(
					new ReturnCallback() {
						@Override
						public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
						}
					});
			return rabbitMqSender;

		}else {
			return rabbitMqSender;

		}
		
	}

}
