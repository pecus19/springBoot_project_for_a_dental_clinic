package ee.ttu.veebirakendus.dentalclinic.availabletimes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableTimesRepository extends JpaRepository<AvailableTimes, Integer> {
}
