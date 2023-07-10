package ee.ttu.veebirakendus.dentalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ttu.veebirakendus.dentalclinic.AbstractIntegration;
import ee.ttu.veebirakendus.dentalclinic.client.Client;
import ee.ttu.veebirakendus.dentalclinic.client.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest extends AbstractIntegration {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void getClient() throws Exception {
        Client savedClient = Client.builder()
                .firstName("Name")
                .lastName("Surname")
                .personalCode(111111L)
                .build();
        clientRepository.save(savedClient);

        ResultActions getResult = mvc.perform(get("/client")
                .param("id", String.valueOf(savedClient.getId()))
                .with(user("user")));
        getResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(savedClient.getFirstName())));
    }

    @Test
    void getClients() throws Exception {
        List<Client> listOfClients = new ArrayList<>();
        Client savedClient = Client.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .personalCode(111111111L)
                .build();
        listOfClients.add(savedClient);

        Client savedClient2 = Client.builder()
                .firstName("Nikita")
                .lastName("Kirejev")
                .personalCode(11111111112L)
                .build();
        listOfClients.add(savedClient2);
        clientRepository.saveAll(listOfClients);
        mvc.perform(get("/client")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(listOfClients.size())));
    }

    @Test
    void addClient() throws Exception {
        Client client = Client.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .personalCode(11111111111L)
                .build();
        ResultActions postResult = mvc.perform(post("/client").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));
        postResult.
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(client.getLastName())));

    }

    @Test
    void deleteClient() throws Exception {
        Client savedClient = Client.builder()
                .firstName("Name")
                .lastName("Surname")
                .personalCode(111111L)
                .build();
        clientRepository.save(savedClient);

        ResultActions deleteResponse = mvc.perform(delete("/client", savedClient.getId())
                .param("id", String.valueOf(savedClient.getId()))
                .with(user("user")));
        deleteResponse.andExpect(status().isOk());

        ResultActions getResponse = mvc.perform(get("/client", savedClient.getId())
                .param("id", String.valueOf(savedClient.getId()))
                .with(user("user")));
        getResponse.andExpect(status().isNotFound());
    }

    @Test
    void updateClient() throws Exception {
        Client savedClient = Client.builder()
                .firstName("Name")
                .lastName("Surname")
                .personalCode(11111111111L)
                .build();
        clientRepository.save(savedClient);

        Client updatedClient = Client.builder()
                .id(savedClient.getId())
                .firstName("Danila")
                .lastName("Kirejev")
                .personalCode(11111111111L)
                .build();
        clientRepository.save(updatedClient);

        ResultActions putResult = mvc.perform(put("/client")
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedClient)));

        putResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedClient.getFirstName())));
    }
}
