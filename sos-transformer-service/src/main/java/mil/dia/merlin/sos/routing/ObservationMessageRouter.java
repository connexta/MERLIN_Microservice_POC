package mil.dia.merlin.sos.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
class ObservationMessageRouter {
    static final String KAFKA_GROUP_ID = "merlin-observation-message-router";
    static final String OBSERVATION_INPUT = "merlin-observation-input";

    private final Consumer<String> observationMessageConsumer;

    private Logger logger = LoggerFactory.getLogger(ObservationMessageRouter.class);

    private Function<String, String> observationMessageTransformer;

    public ObservationMessageRouter(Function<String, String> observationMessageTransformer,
                                    Consumer<String> observationMessageConsumer) {
        this.observationMessageTransformer = observationMessageTransformer;
        this.observationMessageConsumer = observationMessageConsumer;
    }

    @KafkaListener(id = KAFKA_GROUP_ID, topics = {OBSERVATION_INPUT})
    public void onObservation(String observationText) {
        logger.info("Received Observation: " + observationText);
        String newMessage = observationMessageTransformer.apply(observationText);
        observationMessageConsumer.accept(newMessage);
        logger.info("Published Observation: " + newMessage);
    }
}
