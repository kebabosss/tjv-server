package fit.bitjv.semestral;

import fit.bitjv.semestral.repositoriesOld.MovieDAO;
import fit.bitjv.semestral.repositoriesOld.MovieJPARep;
import fit.bitjv.semestral.repositoriesOld.MovieMapRep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfig {
    @Value("${customer.repository}")
    String rep;
    @Bean
    MovieDAO movieDAO() {
        return switch (rep) {
            case "JPA" -> new MovieJPARep();
            case "Mem" -> new MovieMapRep();
            default -> throw new RuntimeException();
        };
    }

}
