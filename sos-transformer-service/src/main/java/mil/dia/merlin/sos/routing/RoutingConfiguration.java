package mil.dia.merlin.sos.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Consumer;

@Configuration
class RoutingConfiguration {
    static final String SENSOR_OUTPUT = "merlin-sensors-json";
    static final String OBSERVATION_OUTPUT = "merlin-observations-json";
    static final String SENSORS_UI_OUTPUT = "merlin-sensors-ui";

    @Bean
    public Consumer<String> sensorMessageConsumer(KafkaTemplate kafkaTemplate) {
        return s -> kafkaTemplate.send(SENSOR_OUTPUT, s);
    }

    @Bean
    public Consumer<String> observationMessageConsumer(KafkaTemplate kafkaTemplate) {
        return s -> kafkaTemplate.send(OBSERVATION_OUTPUT, s);
    }

    @Bean
    public Consumer<String> sensorUIMessageConsumer(KafkaTemplate kafkaTemplate) {
        return s -> kafkaTemplate.send(SENSORS_UI_OUTPUT, s);
    }
}
