package se.iths.TwoToeSebastian.myservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class SetupUserDatabase {

    @Bean
    CommandLineRunner initDatabase(UserDataRepository repository) {
        return args -> {
            if( repository.count() == 0) {
                //New empty database, add some persons
                repository.save(new UserData(0,"Anton", "Anton Johansson", "Mölndal", 10000, true));
                repository.save(new UserData(0,"Sebbe", "Sebastian Waltilla", "Fjärås", 60000, true));
                repository.save(new UserData(0,"Jonte", "Jonathan Holm", "Umeå", 40000, false));
            }
        };
    }

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}