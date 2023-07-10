package ee.ttu.veebirakendus.dentalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ttu.veebirakendus.dentalclinic.AbstractIntegration;
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
class EmployeeControllerTest extends AbstractIntegration {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void getEmployee() throws Exception {
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

        mvc.perform(get("/employee")
                        .param("id", String.valueOf(savedEmployee.getId()))
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(savedEmployee.getFirstName())));
    }

    @Test
    void getSortedBy() throws Exception {
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

        mvc.perform(get("/employee/sort")
                        .param("page", "0")
                        .param("orderBy", "firstName")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(savedEmployee.getFirstName())));
    }


    @Test
    void getSearch() throws Exception {
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

        mvc.perform(get("/employee/search")
                        .param("page", "0")
                        .param("orderBy", "firstName")
                        .param("order", "ASC")
                        .param("firstName", "Danila")
                        .param("lastName", "Kirejev")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(savedEmployee.getFirstName())));
    }

    @Test
    void getEmployeeByPersonalCode() throws Exception {
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

        mvc.perform(get("/employee/personal_code")
                        .param("personalCode", String.valueOf(savedEmployee.getPersonalCode()))
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(savedEmployee.getFirstName())));
    }

    @Test
    void getEmployees() throws Exception {
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee savedEmployee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        listOfEmployees.add(savedEmployee);

        Employee savedEmployee2 = Employee.builder()
                .firstName("Nikita")
                .lastName("Kirejev")
                .age(15)
                .weight(80)
                .height(190)
                .sex("M")
                .personalCode(11111111112L)
                .build();
        listOfEmployees.add(savedEmployee2);
        employeeRepository.saveAll(listOfEmployees);
        mvc.perform(get("/employee")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));
    }


    @Test
    void updateEmployee() throws Exception {
        Employee savedEmployee = Employee.builder()
                .firstName("Nikita")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .id(savedEmployee.getId())
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(updatedEmployee);

        ResultActions putResult = mvc.perform(put("/employee")
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        putResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())));
    }

    @Test
    void addEmployee() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Danila")
                .lastName("Kirejev")
                .age(20)
                .weight(77)
                .height(187)
                .sex("M")
                .personalCode(11111111111L)
                .build();

        ResultActions postResult = mvc.perform(post("/employee").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        postResult.
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())));

    }

    @Test
    void deleteEmployee() throws Exception {
        Employee savedEmployee = Employee.builder()
                .firstName("Name")
                .lastName("Surname")
                .age(20)
                .weight(70)
                .height(180)
                .sex("F")
                .personalCode(11111111111L)
                .build();
        employeeRepository.save(savedEmployee);

        ResultActions deleteResponse = mvc.perform(delete("/employee")
                .param("id", String.valueOf(savedEmployee.getId()))
                .with(user("user")));
        deleteResponse.andExpect(status().isOk());

        ResultActions getResponse = mvc.perform(get("/employee")
                .param("id", String.valueOf(savedEmployee.getId()))
                .with(user("user")));
        getResponse.andExpect(status().isNotFound());
    }
}
