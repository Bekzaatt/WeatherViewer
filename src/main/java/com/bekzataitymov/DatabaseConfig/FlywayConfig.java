package com.bekzataitymov.DatabaseConfig;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;

@Configuration
@ComponentScan(basePackages= "com.bekzataitymov")
public class FlywayConfig {
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

        flyway.migrate();


        return flyway;
    }
}
