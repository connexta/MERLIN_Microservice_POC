package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class SensorMessageRouter {
    private Logger logger = LoggerFactory.getLogger(SensorMessageRouter.class);

    private KafkaTemplate kafkaTemplate;

    public SensorMessageRouter(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(id = "merlin-sensor-message-router", topics = {"merlin-sensor-input"})
    public void onSensor(String sensorText) {
        logger.info("Received: " + sensorText);
        kafkaTemplate.send("merlin-sensors-json", transform(sensorText));
    }

    private String transform(String input) {
        return input;
    }
}
