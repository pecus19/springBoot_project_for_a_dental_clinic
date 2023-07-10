package ee.ttu.veebirakendus.dentalclinic.availabletimes;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081", "http://iti0302dentalclinic.hopto.org/", "http://193.40.255.20/"})
@RestController
@RequiredArgsConstructor
public class AvailableTimesController {
    private final AvailableTimesService availableTimesService;

    @GetMapping("/time")
    public List<AvailableTimesDTO> getAllAvailableTimes() {
        return availableTimesService.findAll();
    }

    @GetMapping(value = "/time", params = "id")
    public AvailableTimesDTO getAvailableTimes(@RequestParam("id") int id) {
        return availableTimesService.getAvailableTimesById(id);
    }

    @PostMapping("/time")
    public AvailableTimesDTO addAvailableTimes(@RequestBody AvailableTimesDTO availableTimesDTO) {
        return availableTimesService.saveAvailableTimes(availableTimesDTO);
    }

    @PutMapping("/time")
    public AvailableTimesDTO update(@RequestBody AvailableTimesDTO availableTimesDTO) {
        return availableTimesService.saveAvailableTimes(availableTimesDTO);
    }

    @DeleteMapping("/time")
    public ResponseEntity<String> delete(@RequestParam int id) {
        return availableTimesService.deleteAvailableTime(id);
    }
}
