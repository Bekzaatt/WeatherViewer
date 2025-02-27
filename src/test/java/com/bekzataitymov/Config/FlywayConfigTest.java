package com.bekzataitymov.Config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

@Configuration
@ComponentScan(basePackages= "com.bekzataitymov")
public class FlywayConfigTest {
    @Value("${flyway.placeholders.id_type}")
    private String idType;
    @Bean
    public Flyway flyway(DataSource dataSource) throws SQLException {
        System.out.println("Id_type: " + idType);

        if(dataSource == null){
            System.out.println("dataSource is null");
        }
        else {
            System.out.println("dataSource is not null");
        }
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource).placeholders(Map.of("id_type", idType))
                .schemas("PUBLIC").cleanDisabled(false)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .createSchemas(true)
                .connectRetries(3)
                .load();


        System.out.println("Flyway database URL: " + dataSource.getConnection().getMetaData().getURL());
        System.out.println("Flyway found before  migrations: " + flyway.info().pending().length);
        System.out.println("Applied migrations after clean: " + flyway.info().applied().length);
        flyway.clean();
        flyway.migrate();

        System.out.println("Flyway found after  migrations: " + flyway.info().pending().length);
        System.out.println("Flyway locations: " + Arrays.toString(Flyway.configure().getLocations()));
        System.out.println("Applied after migrations: " + flyway.info().applied().length);

        return flyway;
    }
}
