package com.nitinrane.learning.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class MessagingRedisApplication {

	private static final Logger log = LoggerFactory.getLogger(MessagingRedisApplication.class);
	@Bean
	public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	public Receiver receiver() {
		return new Receiver();
	}

	@Bean
	public MessageListenerAdapter listenerAdapter() {
		return new MessageListenerAdapter(receiver(), "receiveMessage");
	}

	@Bean
	public RedisMessageListenerContainer messageListenerContainer(MessageListenerAdapter listenerAdapter,
														   RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
		container.addMessageListener(listenerAdapter, new PatternTopic("chat123"));
		return container;
	}

	public static void main(String[] args) throws InterruptedException {
		final ApplicationContext appContext = SpringApplication.run(
				MessagingRedisApplication.class, args);

		final Receiver receiver = appContext.getBean(Receiver.class);
		final StringRedisTemplate redisTemplate = appContext.getBean(StringRedisTemplate.class);

		while(receiver.getCount() == 0) {
			log.info("sending message..");
			redisTemplate.convertAndSend("chat", "Hello from Redis!");
			redisTemplate.convertAndSend("chat", "Another msg from Redis!");
			redisTemplate.convertAndSend("chat123", "Hello from Redis different topic!");

			Thread.sleep(500);
		}

		System.exit(0);
	}

}
