package mil.dia.merlin.sos.transformation;

import mil.dia.swe.JSONFilter;
import mil.dia.swe.SWETransform;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
class TransformerConfiguration {
    private static final String TRANSFORM_FILTER = ".procedureDescription | (.identification.identifier| map({(.name)" +
            ": (.value)}) | add) + (.identification.identifier| map(if .name == \"uniqueID\" then " +
            "{\"assignedProcedure\": (.value), \"assignedOffering\": (.value)} else null end) | add) + (if " +
            ".classification.classifier? then .classification.classifier | map(if .name? then {(.name): (.value)} " +
            "else null end) | add else null end) + (.validTime | if .type == \"TimeInstant\" then " +
            "{\"validTimeStart\": (.timePosition.value)} else {\"validTimeStart\": (.beginPosition), " +
            "\"validTimeEnd\": (.endPosition)} end) + (if .securityConstraint? then {\"securityConstraint\": " +
            ".securityConstraint} else null end) + (if .legalConstraint? then {\"legalConstraint\": .legalConstraint}" +
            " else null end) + (if .member.location? then {\"sensorLocation\": (.member.location)} else null end)";

    private final Logger logger = LoggerFactory.getLogger(TransformerConfiguration.class);

    @Bean
    public Function<String, String> sensorMessageTransformer() {
        return SWETransform::insertSensorToJSON;
    }

    @Bean
    public Function<String, String> observationMessageTransformer() {
        return s -> s;
    }

    @Bean
    public Function<String, String> sensorUIMessageTransformer() throws JsonQueryException {
        JSONFilter jsonFilter = new JSONFilter(TRANSFORM_FILTER);
        return jsonFilter::filter;
    }
}
