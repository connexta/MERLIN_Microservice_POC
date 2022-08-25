package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class SensorMessageRouter {
    public static final String MERLIN_SENSOR_MESSAGE_ROUTER_KAFKA_ID = "merlin-sensor-message-router";
    public static final String MERLIN_SENSOR_INPUT_TOPIC = "merlin-sensor-input";
    public static final String MERLIN_SENSORS_JSON_TOPIC = "merlin-sensors-json";

    private Logger logger = LoggerFactory.getLogger(SensorMessageRouter.class);

    private KafkaTemplate kafkaTemplate;

    private Function<>

    public SensorMessageRouter(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(id = MERLIN_SENSOR_MESSAGE_ROUTER_KAFKA_ID, topics = {MERLIN_SENSOR_INPUT_TOPIC})
    public void onSensor(String sensorText) {
        logger.info("Received: " + sensorText);
        kafkaTemplate.send(MERLIN_SENSORS_JSON_TOPIC, transform(sensorText));
    }

    private String transform(String input) {
        return input;
    }
}
