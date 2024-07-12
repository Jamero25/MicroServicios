package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.demo.dao.CuentaDTO;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Bean
	ConsumerFactory<String, CuentaDTO> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
//		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(JsonSerializer.TYPE_MAPPINGS, "com.example.demo:com.example.demo.dao.CuentaDTO");
		final JsonDeserializer<CuentaDTO> jsonDeserializer = new JsonDeserializer<>();
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, CuentaDTO> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CuentaDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
