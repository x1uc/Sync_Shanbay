package top.x1uc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransYDaoToSBay {

    public static void main(String[] args) {
        SpringApplication.run(TransYDaoToSBay.class, args);
    }
}
