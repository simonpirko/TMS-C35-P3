package by.tms.tmsc35p3;

import org.flywaydb.core.Flyway;
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

    public static void main(String[] args) {
        SpringApplication.run(TmsC35P3Application.class, args);
    }

}
