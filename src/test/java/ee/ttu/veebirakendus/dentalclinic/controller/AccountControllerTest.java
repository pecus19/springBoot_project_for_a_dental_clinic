package ee.ttu.veebirakendus.dentalclinic.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ttu.veebirakendus.dentalclinic.AbstractIntegration;
import ee.ttu.veebirakendus.dentalclinic.account.Account;
import ee.ttu.veebirakendus.dentalclinic.account.AccountDTO;
import ee.ttu.veebirakendus.dentalclinic.account.AccountRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest extends AbstractIntegration {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void getAccount() throws Exception {
        Account savedAccount = Account.builder()
                .email("nikitakirejev26@gmail.com")
                .hashPassword("12345")
                .build();
        accountRepository.save(savedAccount);

        ResultActions getResult = mvc.perform(get("/account")
                .param("id", String.valueOf(savedAccount.getId()))
                .with(user("user")));
        getResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(savedAccount.getEmail())));

    }

    @Test
    void deleteAccount() throws Exception {
        Account savedAccount = Account.builder()
                .email("vimist@gmail.com")
                .hashPassword("12345")
                .build();
        accountRepository.save(savedAccount);

        ResultActions deleteResponse = mvc.perform(delete("/account")
                .param("email", savedAccount.getEmail())
                .with(user("user")));
        deleteResponse.andExpect(status().isOk());

        ResultActions getResponse = mvc.perform(get("/account")
                .param("id", String.valueOf(savedAccount.getId()))
                .with(user("user")));
        getResponse.andExpect(status().isNotFound());
    }

    @Test
    void addAccount() throws Exception {
        AccountDTO accountDTO = AccountDTO.builder()
                .email("andrey@gmail.com")
                .plainPassword("12345")
                .build();

        ResultActions postResult = mvc.perform(post("/public/account").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)));

        postResult.
                andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(accountDTO.getEmail())));
    }

    @Test
    void token() throws Exception {
        AccountDTO accountDTO = AccountDTO.builder()
                .email("andrey@gmail.com")
                .plainPassword("12345")
                .build();

        mvc.perform(post("/public/account")
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)));

        ResultActions postResult = mvc.perform(post("/public/account/token")
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)));

        postResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));
    }

    @Test
    void getAccounts() throws Exception {
        List<Account> listOfAccounts = new ArrayList<>();
        Account savedAccount = Account.builder()
                .email("andrey@gmail.com")
                .hashPassword("12345")
                .build();
        listOfAccounts.add(savedAccount);

        Account savedAccount2 = Account.builder()
                .email("vika@gmail.com")
                .hashPassword("123456")
                .build();
        listOfAccounts.add(savedAccount2);
        accountRepository.saveAll(listOfAccounts);
        mvc.perform(get("/account")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(listOfAccounts.size())));
    }
}
