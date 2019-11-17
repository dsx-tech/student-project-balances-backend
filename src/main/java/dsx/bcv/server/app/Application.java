package dsx.bcv.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "dsx/bcv/server")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        init();
        SpringApplication.run(Application.class, args);
    }

    private static void init(){
        System.setProperty("server.servlet.context-path", "/bcv");
        System.getProperties().put("server.port", 9999);
    }
}
