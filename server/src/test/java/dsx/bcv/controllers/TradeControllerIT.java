package dsx.bcv.controllers;

import dsx.bcv.app.Application;
import dsx.bcv.data.models.Trade;
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
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final String controllerUrl = "/trades/";

    private Trade trade1;
    private Trade trade2;
    private Trade trade3;

    @Before
    public void setUp() throws Exception {

        trade1 = new Trade(
                LocalDateTime.now(),
                "BTCUSD",
                "Sell",
                new BigDecimal("0.00097134"),
                "BTC",
                new BigDecimal("10142.28001"),
                "USD",
                new BigDecimal("0.02"),
                "USD",
                "37387684");

        trade2 = new Trade(
                LocalDateTime.now(),
                "ETHBTC",
                "Buy",
                new BigDecimal("0.001"),
                "ETH",
                new BigDecimal("0.021"),
                "BTC",
                new BigDecimal("0.0000025"),
                "USD",
                "37381155");

        trade3 = new Trade(
                LocalDateTime.now(),
                "RUBEUR",
                "Sell",
                new BigDecimal("500"),
                "RUB",
                new BigDecimal("71.22"),
                "EUR",
                new BigDecimal("100"),
                "RUB",
                "37381155");

        mockMvc.perform(post(controllerUrl)
                .content(toJson(trade1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        mockMvc.perform(post(controllerUrl)
                .content(toJson(trade2))
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
                .andExpect(jsonPath("$[*].instrument").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getByID() throws Exception {

        long id = 2;

        mockMvc.perform(get(controllerUrl + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.instrument").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void add() throws Exception {

        mockMvc.perform(post(controllerUrl)
                .content(toJson(trade3))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.instrument").value(trade3.getInstrument()));
    }

    @Test
    public void update() throws Exception {

        long id = 1;

        mockMvc.perform(
                put(controllerUrl + id)
                .content(toJson(trade1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.instrument").value(trade1.getInstrument()))
                .andReturn();
    }

    @Test
    public void delete() throws Exception {

        long id = 0;

        mockMvc.perform(MockMvcRequestBuilders.delete(controllerUrl + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete(controllerUrl + id))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}