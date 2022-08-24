package mil.dia.merlin.kafka.initial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaInitializerApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(KafkaInitializerApplication.class);

    public static void main(String... args) {
        SpringApplication.run(KafkaInitializerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Completed creating topics.");
    }
}
