package ee.ttu.veebirakendus.dentalclinic.availabletimes;

import ee.ttu.veebirakendus.dentalclinic.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AvailableTimesService {
    private final AvailableTimesRepository availableTimesRepository;
    private final AvailableTimesMapper availableTimesMapper;

    private AvailableTimes getById(int id) {
        return availableTimesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Available time with id %d was not found.".formatted(id)));
    }

    public List<AvailableTimesDTO> findAll() {
        return availableTimesMapper.toDtoList(availableTimesRepository.findAll());
    }

    public AvailableTimesDTO saveAvailableTimes(AvailableTimesDTO availableTimesDTO) {
        return availableTimesMapper.toDto(availableTimesRepository.save(availableTimesMapper.fromDto(availableTimesDTO)));
    }

    public ResponseEntity<String> deleteAvailableTime(int id) {
        AvailableTimes availableTimes = getById(id);
        availableTimesRepository.delete(availableTimes);
        return new ResponseEntity<>("Available time was successfully deleted.", HttpStatus.OK);
    }

    public AvailableTimesDTO getAvailableTimesById(int id) {
        return availableTimesMapper.toDto(getById(id));
    }
}
