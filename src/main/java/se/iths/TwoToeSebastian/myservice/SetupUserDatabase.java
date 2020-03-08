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
                //New empty database, add some UserData
                repository.save(new UserData(0,"Johanna", "Johnna Johansson", "Örgryte", 11000, true));
                repository.save(new UserData(0,"Sebbe", "Sebastian Waltilla", "Fjärås", 30000, true));
                repository.save(new UserData(0,"Donnald", "Donnald Duck", "Umeå", 120000, false));
            }
        };
    }

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}