package com.edwin.edwin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseTester implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTester(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("\n=== DB 연결 테스트 ===");
            System.out.println("DB 제품: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("DB 버전: " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("연결 상태: 정상 ✔\n");
        } catch (SQLException e) {
            System.err.println("\n!!! DB 연결 실패 !!!");
            e.printStackTrace();
        }
    }
} 