package ilcarro.ilcarro;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IlcarroApplication {

    public static void main(String[] args) {
        SpringApplication.run(IlcarroApplication.class, args);
    }

    @Bean
    public Map<Long, String> confCodeMap(){
    	return new ConcurrentHashMap<Long, String>();
    }
}
