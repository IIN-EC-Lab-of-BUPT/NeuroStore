package bupt.embemc;

import bupt.embemc.service.Mything;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
//@EnableAutoConfiguration
@SpringBootApplication
public class App {



    @RequestMapping("/")
    String home() {
        return "Welcome to NeuroStore!";
    }
    @RequestMapping("/thing")
    public Mything thing() {
        return new Mything();
    }
    
    public static void main(String[] args) {
//        System.out.println(App.class)

        SpringApplication.run(App.class, args);


//        StartListener startListener = new StartListener();
//        startListener.onApplicationEvent();
    }
}