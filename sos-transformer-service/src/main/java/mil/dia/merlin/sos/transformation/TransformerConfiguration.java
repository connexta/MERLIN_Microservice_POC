package mil.dia.merlin.sos.transformation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
class TransformerConfiguration {
    @Bean
    public Function<String, String> sensorMessageTransformer() {
        return s -> s;
    }

    @Bean
    public Function<String, String> observationMessageTransformer() {
        return s -> s;
    }
}
