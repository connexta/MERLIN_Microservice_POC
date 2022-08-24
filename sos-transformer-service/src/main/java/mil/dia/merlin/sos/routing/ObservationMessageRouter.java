package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class ObservationMessageRouter {
    private Logger logger = LoggerFactory.getLogger(ObservationMessageRouter.class);

    private KafkaTemplate kafkaTemplate;

    public ObservationMessageRouter(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(id = "merlin-observation-message-router", topics = {"merlin-observation-input"})
    public void onSensor(String sensorText) {
        logger.info("Received: " + sensorText);
        kafkaTemplate.send("merlin-observations-json", transform(sensorText));
    }

    private String transform(String input) {
        return input;
    }
}
