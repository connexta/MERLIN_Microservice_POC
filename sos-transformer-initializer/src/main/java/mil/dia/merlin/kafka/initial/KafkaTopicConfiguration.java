package mil.dia.merlin.kafka.initial;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
class KafkaTopicConfiguration {
    @Value("${mil.dia.merlin.sos.kafka.partition-count:1}")
    private Integer partitionCount;

    @Value("${mil.dia.merlin.sos.kafka.replica-count:1}")
    private Integer replicaCount;

    @Value("${mil.dia.merlin.sos.kafka.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic sensorInput() {
        return TopicBuilder.name("merlin-sensor-input")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic observationInput() {
        return TopicBuilder.name("merlin-observation-input")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic observationsXml() {
        return TopicBuilder.name( "merlin-observations-xml")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic observationsJson() {
        return TopicBuilder.name("merlin-observations-json")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic sensorsXml() {
        return TopicBuilder.name( "merlin-sensors-xml")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

    @Bean
    public NewTopic sensorsJson() {
        return TopicBuilder.name("merlin-sensors-json")
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }
}

