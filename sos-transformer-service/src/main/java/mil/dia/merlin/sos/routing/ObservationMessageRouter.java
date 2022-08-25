package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class ObservationMessageRouter {
    public static final String MERLIN_OBSERVATION_MESSAGE_ROUTER_KAFKA_ID = "merlin-observation-message-router";
    public static final String MERLIN_OBSERVATION_INPUT_TOPIC = "merlin-observation-input";
    public static final String MERLIN_OBSERVATIONS_JSON_TOPIC = "merlin-observations-json";

    private Logger logger = LoggerFactory.getLogger(ObservationMessageRouter.class);

    private KafkaTemplate kafkaTemplate;

    public ObservationMessageRouter(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(id = MERLIN_OBSERVATION_MESSAGE_ROUTER_KAFKA_ID, topics = {MERLIN_OBSERVATION_INPUT_TOPIC})
    public void onSensor(String sensorText) {
        logger.info("Received: " + sensorText);
        kafkaTemplate.send(MERLIN_OBSERVATIONS_JSON_TOPIC, transform(sensorText));
    }

    private String transform(String input) {
        return input;
    }
}
