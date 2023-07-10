package ee.ttu.veebirakendus.dentalclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DentalClinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(DentalClinicApplication.class, args);
    }
}
