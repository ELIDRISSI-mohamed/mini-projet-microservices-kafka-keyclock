package org.demo.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.demo.consumer.dto.BillDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffset;

    private Map<String, Object> getConsumerProperties() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        propertiesMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propertiesMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);

        return propertiesMap;
    }
    @Bean
    public ConsumerFactory<String, BillDTO> defaultConsumerFactory() {
        Map<String, Object> consumerProperties = getConsumerProperties();
        JsonDeserializer<BillDTO> jsonDeserializer = new JsonDeserializer<>(BillDTO.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory(consumerProperties, new StringDeserializer(), jsonDeserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BillDTO> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BillDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(defaultConsumerFactory());
        return factory;
    }
}
