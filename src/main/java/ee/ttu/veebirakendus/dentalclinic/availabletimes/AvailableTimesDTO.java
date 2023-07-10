package ee.ttu.veebirakendus.dentalclinic.availabletimes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableTimesDTO {
    private Integer id;
    private String time;
    private String doctorName;
}
