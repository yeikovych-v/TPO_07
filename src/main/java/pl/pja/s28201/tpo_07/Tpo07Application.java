package pl.pja.s28201.tpo_07;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.pja.s28201.tpo_07.repository.InMemoryCodeFormatRepository;
import pl.pja.s28201.tpo_07.service.FileAndSerializationService;

@SpringBootApplication
public class Tpo07Application {

    public static void main(String[] args) {
        SpringApplication.run(Tpo07Application.class, args);
    }

    @Bean
    public CommandLineRunner run(InMemoryCodeFormatRepository inMemoryDb, FileAndSerializationService fileAndSerializationService) {
        return args -> {
            inMemoryDb.addAll(fileAndSerializationService.deserializeAll().values());
            fileAndSerializationService.deleteOnTimeout();
        };
    }
}
