package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class SensorUIMessageRouter {
    static final String SENSORS_JSON = "merlin-sensors-json";
    static final String KAFKA_GROUP_ID = "merlin-sensor-ui-router";
    private Logger logger = LoggerFactory.getLogger(SensorMessageRouter.class);

    private Function<String, String> sensorUIMessageTransformer;

    private Consumer<String> sensorUIMessageConsumer;

    public SensorUIMessageRouter(Function<String, String> sensorUIMessageTransformer,
                                 Consumer<String> sensorUIMessageConsumer) {
        this.sensorUIMessageTransformer = sensorUIMessageTransformer;
        this.sensorUIMessageConsumer = sensorUIMessageConsumer;
    }

    @KafkaListener(id = KAFKA_GROUP_ID, topics = {SENSORS_JSON})
    public void transformAndForward(String sensorText) {
        logger.info("Received Sensor: " + sensorText);
        String newMessage = sensorUIMessageTransformer.apply(sensorText);
        sensorUIMessageConsumer.accept(newMessage);
        logger.info("Published Sensor: " + newMessage);
    }
}
