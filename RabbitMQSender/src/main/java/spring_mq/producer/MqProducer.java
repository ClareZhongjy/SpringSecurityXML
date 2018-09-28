package spring_mq.producer;

public interface MqProducer {
	/**
	 * 发送消息到mq
	 * @param queueKey
	 * @param object
	 */
	public void sendDataToQueue(String queueKey,Object object);
}
