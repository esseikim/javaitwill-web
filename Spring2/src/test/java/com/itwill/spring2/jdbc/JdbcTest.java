package com.itwill.spring2.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleDriver;

@Slf4j // 로그 사용.
@ExtendWith(SpringExtension.class) // Spring Junit 테스트를 실행하는 메인 클래스.
@ContextConfiguration(
 locations = {"file:src/main/webapp/WEB-INF/application-context.xml"}        
 ) // 스프링 컨텍스트(application-context.xml) 환경 설정 파일의 경로와 이름.
public class JdbcTest {
 
    @Test // JUnit 테스트 메서드
    public void testOjdbc() throws SQLException {
        // JDBC 1: DriverManager에 JDBC를 등록.
        DriverManager.registerDriver(new OracleDriver());
        log.info("Oracle JDBC 드라이버 등록 성공");
        
        // JDBC 2: Connection 객체 생성.
        final String url = "jdbc:oracle:thin:@localhost:1521:xe"; // localhost 대신 ip 주소 입력도 가능
        final String username = "scott";
        final String password = "tiger";
        Connection conn = DriverManager.getConnection(url, username, password); // 실제로 물리적 접속을 맺음. 서버에서 요청이 들어올 때마다 접속을 맺는건 DB에 부하.  
        // cf) 서버가 실행이 될 때 Connection들을 Connection pool에 생성, HikariCP 라이브러리 pom.xml 추가.
        
        Assertions.assertNotNull(conn); // 단위 테스트 조건: Connection 객체가 null이 아님.
        log.info("conn = {}", conn);
        
        // JDBC 3: 사용했던 리소스 해제. 
        conn.close();
        log.info("connection close 성공");
    }
}
