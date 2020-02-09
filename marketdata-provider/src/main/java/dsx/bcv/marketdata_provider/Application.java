package dsx.bcv.marketdata_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        init();
        SpringApplication.run(Application.class, args);
    }

    private static void init(){
        System.setProperty("server.servlet.context-path", "/bcv");
        System.getProperties().put("server.port", 8888);
    }
}
