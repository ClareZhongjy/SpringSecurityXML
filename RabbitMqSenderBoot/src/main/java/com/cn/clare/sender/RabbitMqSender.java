package com.cn.clare.sender;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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

			//如果ack=false，则是消息未到达exchange
			rabbitTemplate.setConfirmCallback(
					new RabbitTemplate.ConfirmCallback() {
						@Override
						public void confirm(CorrelationData correlationData, boolean ack, String cause) {
							if(!ack){
								System.out.println("The message could deliver to exchange!"+cause+","+correlationData.toString());
							}
						}
					}
			);


            //需设置mandatory=true,否则无论是否到达queue，均不回调
            //exchange到queue成功，则不回调,失败则回调
			rabbitTemplate.setMandatory(true);
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
