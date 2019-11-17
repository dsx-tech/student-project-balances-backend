package dsx.bcv.app;

import dsx.bcv.data.models.CurrencyPair;
import dsx.bcv.services.CurrencyPairService;
import javafx.util.Pair;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.ArrayList;

@ComponentScan(value = "dsx/bcv")
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, ParseException {
        init();
        SpringApplication.run(Application.class, args);
    }

    private static void init(){
        System.setProperty("server.servlet.context-path", "/bcv");
        System.getProperties().put("server.port", 9999);
    }
}
