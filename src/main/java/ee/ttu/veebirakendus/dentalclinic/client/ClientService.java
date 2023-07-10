package ee.ttu.veebirakendus.dentalclinic.client;

import ee.ttu.veebirakendus.dentalclinic.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    private Client getById(int id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id %d was not found.".formatted(id)));
    }

    public List<ClientDTO> findAll() {
        return clientMapper.toDtoList(clientRepository.findAll());
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        return clientMapper.toDto(clientRepository.save(clientMapper.fromDto(clientDTO)));
    }

    public ResponseEntity<String> deleteClient(int id) {
        Client client = getById(id);
        clientRepository.delete(client);
        return new ResponseEntity<>("Client was successfully deleted.", HttpStatus.OK);
    }

    public ClientDTO getClientById(int id) {
        return clientMapper.toDto(getById(id));
    }
}
