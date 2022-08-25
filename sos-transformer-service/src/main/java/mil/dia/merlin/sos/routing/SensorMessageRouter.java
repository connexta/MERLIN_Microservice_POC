package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
class SensorMessageRouter {
    static final String KAFKA_GROUP_ID = "merlin-sensor-message-router";
    static final String SENSOR_INPUT = "merlin-sensor-input";

    private Logger logger = LoggerFactory.getLogger(SensorMessageRouter.class);

    private Function<String, String> sensorMessageTransformer;

    private Consumer<String> sensorMessageConsumer;

    public SensorMessageRouter(Function<String, String> sensorMessageTransformer,
                               Consumer<String> sensorMessageConsumer) {
        this.sensorMessageTransformer = sensorMessageTransformer;
        this.sensorMessageConsumer = sensorMessageConsumer;
    }

    @KafkaListener(id = KAFKA_GROUP_ID, topics = {SENSOR_INPUT})
    public void transformAndForward(String sensorText) {
        logger.info("Received Sensor: " + sensorText);
        String newMessage = sensorMessageTransformer.apply(sensorText);
        sensorMessageConsumer.accept(newMessage);
        logger.info("Published Sensor: " + newMessage);
    }
}
