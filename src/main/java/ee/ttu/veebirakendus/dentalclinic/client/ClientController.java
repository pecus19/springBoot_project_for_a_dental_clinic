package ee.ttu.veebirakendus.dentalclinic.client;

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
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/client")
    public List<ClientDTO> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping(value = "/client", params = "id")
    public ClientDTO getClientById(@RequestParam("id") int id) {
        return clientService.getClientById(id);
    }

    @PostMapping("/client")
    public ClientDTO addClient(@RequestBody ClientDTO clientDTO) {
        return clientService.saveClient(clientDTO);
    }

    @PutMapping("/client")
    public ClientDTO update(@RequestBody ClientDTO clientDTO) {
        return clientService.saveClient(clientDTO);
    }

    @DeleteMapping("/client")
    public ResponseEntity<String> delete(@RequestParam int id) {
        return clientService.deleteClient(id);
    }
}
