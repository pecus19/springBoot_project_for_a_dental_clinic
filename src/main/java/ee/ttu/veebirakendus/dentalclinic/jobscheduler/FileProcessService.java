package ee.ttu.veebirakendus.dentalclinic.jobscheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
@Slf4j
public class FileProcessService {
    public void processFiles() throws InterruptedException {
        LOGGER.info("Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }
}
