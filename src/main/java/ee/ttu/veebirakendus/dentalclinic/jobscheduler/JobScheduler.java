package ee.ttu.veebirakendus.dentalclinic.jobscheduler;

import ee.ttu.veebirakendus.dentalclinic.jobscheduler.dto.JokeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Component
@RequiredArgsConstructor
public class JobScheduler {
    private final FileProcessService fileProcessService;

    @Scheduled(fixedDelay = 5000)
    public void sayHello() throws InterruptedException {
        LOGGER.info("Hello");
        fileProcessService.processFiles();
    }

    @Scheduled(fixedDelay = 5000)
    public void printAJoke() {
        RestTemplate restTemplate = new RestTemplate();
        JokeResponse response = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", JokeResponse.class);
        if (response != null) {
            LOGGER.info(response.getValue());
        }
    }
}
