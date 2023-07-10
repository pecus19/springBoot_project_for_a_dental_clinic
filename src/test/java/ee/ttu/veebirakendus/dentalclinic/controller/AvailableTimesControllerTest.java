package ee.ttu.veebirakendus.dentalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ttu.veebirakendus.dentalclinic.AbstractIntegration;
import ee.ttu.veebirakendus.dentalclinic.availabletimes.AvailableTimes;
import ee.ttu.veebirakendus.dentalclinic.availabletimes.AvailableTimesRepository;
import ee.ttu.veebirakendus.dentalclinic.employee.Employee;
import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeRepository;
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
class AvailableTimesControllerTest extends AbstractIntegration {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AvailableTimesRepository availableTimesRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        availableTimesRepository.deleteAll();
    }

    @Test
    void getTimes() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(employee);

        List<AvailableTimes> listOfTimes = new ArrayList<>();
        AvailableTimes availableTimes = AvailableTimes.builder()
                .time("2023-01-19 19:21:06")
                .doctor(employee)
                .build();
        listOfTimes.add(availableTimes);

        AvailableTimes availableTimes2 = AvailableTimes.builder()
                .time("2022-01-19 19:21:06")
                .doctor(employee)
                .build();
        listOfTimes.add(availableTimes2);
        availableTimesRepository.saveAll(listOfTimes);
        mvc.perform(get("/time")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(listOfTimes.size())));
    }

    @Test
    void addTime() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(employee);
        AvailableTimes availableTimes = AvailableTimes.builder()
                .time("2022-01-19 19:21:06")
                .doctor(employee)
                .build();
        ResultActions postResult = mvc.perform(post("/time").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(availableTimes)));
        postResult.
                andExpect(status().isOk())
                .andExpect(jsonPath("$.time", is(availableTimes.getTime())));

    }

    @Test
    void updateTimes() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(employee);

        AvailableTimes availableTimes = AvailableTimes.builder()
                .time("2022-01-19 19:21:06")
                .doctor(employee)
                .build();
        availableTimesRepository.save(availableTimes);

        AvailableTimes updatedTimes = AvailableTimes.builder()
                .time("2021-01-19 19:21:06")
                .doctor(employee)
                .build();
        availableTimesRepository.save(updatedTimes);

        ResultActions putResult = mvc.perform(put("/time")
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTimes)));

        putResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.time", is(updatedTimes.getTime())));
    }

    @Test
    void getAvailableTimeById() throws Exception {
        Employee savedEmployee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(savedEmployee);

        AvailableTimes time = AvailableTimes.builder()
                .time("2021-01-19 19:21:06")
                .doctor(savedEmployee)
                .build();
        availableTimesRepository.save(time);

        mvc.perform(get("/time")
                        .param("id", String.valueOf(time.getId()))
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time", is(time.getTime())));
    }

    @Test
    void deleteTimes() throws Exception {
        Employee savedEmployee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(savedEmployee);
        AvailableTimes time = AvailableTimes.builder()
                .time("2021-01-19 19:21:06")
                .doctor(savedEmployee)
                .build();
        availableTimesRepository.save(time);

        ResultActions deleteResponse = mvc.perform(delete("/time", time.getId())
                .param("id", String.valueOf(time.getId()))
                .with(user("user")));
        deleteResponse.andExpect(status().isOk());

        ResultActions getResponse = mvc.perform(get("/time", time.getId())
                .param("id", String.valueOf(time.getId()))
                .with(user("user")));
        getResponse.andExpect(status().isNotFound());
    }
}
