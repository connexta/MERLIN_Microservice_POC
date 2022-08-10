package mil.afdcgs.merlin.sos.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
class SosKafkaConfiguration {
    @Value("${mil.afdcgs.merlin.sos.kafka.partition-count:1}")
    private Integer partitionCount;

    @Value("${mil.afdcgs.merlin.sos.kafka.replica-count:1}")
    private Integer replicaCount;

    @Value("${mil.afdcgs.merlin.sos.kafka.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic input() {
        return TopicBuilder.name("stream-merlin-input")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic capabilitiesXml() {
        return TopicBuilder.name("stream-merlin-capabilities-json")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic capabilitiesJson() {
        return TopicBuilder.name("stream-merlin-input")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic observationsXml() {
        return TopicBuilder.name( "stream-merlin-observations-xml")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic observationsJson() {
        return TopicBuilder.name("stream-merlin-observations-json")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic sensorsXml() {
        return TopicBuilder.name( "stream-merlin-sensors-xml")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic sensorsJson() {
        return TopicBuilder.name("stream-merlin-sensors-json")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory(){
        Map<String,Object> props = new HashMap<String,Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "merlin-phase1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory producerFactory) {
        return new KafkaTemplate<Integer, String>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer,String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

