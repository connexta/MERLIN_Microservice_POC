package mil.dia.merlin.sos.transformation;

import mil.dia.swe.SWETransform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
class TransformerConfiguration {

    @Bean
    public Function<String, String> sensorMessageTransformer() {
        return SWETransform::insertSensorToJSON;
    }

    @Bean
    public Function<String, String> observationMessageTransformer() {
        return s -> s;
    }

    @Bean
    public Function<String, String> sensorUIMessageTransformer() {
        // Add UI transformation function here
        return s -> s;
    }
}
