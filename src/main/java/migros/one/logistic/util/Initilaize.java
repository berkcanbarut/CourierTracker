package migros.one.logistic.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import migros.one.logistic.model.Store;
import migros.one.logistic.service.StoresService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public final class Initilaize {
    private final Logger logger = LoggerFactory.getLogger(Initilaize.class);
    private final StoresService storesService;

    public Initilaize(StoresService storesService) {
        this.storesService = storesService;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            ObjectMapper objectMapper = new ObjectMapper();

            TypeReference<List<Store>> typeReference = new TypeReference<List<Store>>(){};

            ClassPathResource classPathResource = new ClassPathResource("/static/stores.json");

            try(InputStream inputStream = classPathResource.getInputStream()) {
                List<Store> storeList = objectMapper.readValue(inputStream,typeReference);

                storeList.forEach(storesService::save);
                logger.info("Stores database filled");
            }
        };
    }
}
