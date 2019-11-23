package dsx.bcv.controllers;

import dsx.bcv.app.Application;
import dsx.bcv.data.models.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static dsx.bcv.services.ToJsonConverterService.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TransactionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final String controllerUrl = "/transactions/";

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @Before
    public void setUp() throws Exception {

        transaction1 = new Transaction(
                LocalDateTime.now(),
                "Deposit",
                "BTC", new BigDecimal("0.21043"),
                new BigDecimal("0"),
                "Complete",
                "3669993");

        transaction2 = new Transaction(
                LocalDateTime.now(),
                "Withdraw",
                "EUR", new BigDecimal("32.1104"),
                new BigDecimal("0"),
                "Complete",
                "3621995");

        transaction3 = new Transaction(
                LocalDateTime.now(),
                "Deposit",
                "RUB", new BigDecimal("24674.11"),
                new BigDecimal("0"),
                "Complete",
                "1528613");

        mockMvc.perform(post(controllerUrl)
                .content(toJson(transaction1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        mockMvc.perform(post(controllerUrl)
                .content(toJson(transaction2))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }

    @Test
    public void getAll() throws Exception {

        mockMvc.perform(get(controllerUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].transactionType").isNotEmpty())    //?
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getByID() throws Exception {

        mockMvc.perform(get(controllerUrl + "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.transactionType").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void add() throws Exception {

        mockMvc.perform(post(controllerUrl)
                .content(toJson(transaction3))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.transactionType").value(transaction3.getTransactionType()));
    }

    @Test
    public void update() throws Exception {

        mockMvc.perform(
                put(controllerUrl + "1")
                        .content(toJson(transaction1))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.transactionType").value(transaction1.getTransactionType()))
                .andReturn();
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(controllerUrl + "0"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete(controllerUrl + "0"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}