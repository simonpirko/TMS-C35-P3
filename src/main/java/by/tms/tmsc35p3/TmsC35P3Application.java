package by.tms.tmsc35p3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@SpringBootApplication
public class TmsC35P3Application {

    @Bean
    public CommandLineRunner checkFlyway(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("=== FLYWAY DEBUG ===");
                System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());

                // Проверяем существование таблицы flyway_schema_history
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet tables = meta.getTables(null, null, "flyway_schema_history", null);
                if (tables.next()) {
                    System.out.println("Flyway table exists");
                } else {
                    System.out.println("Flyway table NOT exists");
                }
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(TmsC35P3Application.class, args);
    }

}
