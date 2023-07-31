package com.itwill.post.dbcp;

import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

// DBCP(Database Connection Pool - data source라고도 함 (cp)). HikariCP 라이브러리 단위 테스트
// web application: Dbcp Test
public class DbcpTest {
    // Log4j2 기능을 구현한 Slf4j 라이브러리의 로깅 기능을 사용해서 로그 출력:
    private final Logger log = LoggerFactory.getLogger(DbcpTest.class);  // 클래스이름을 주면서 logger 객체 생성. 자동으로 com.itwill.post.dbcp.DbcpTest 출력이 됨.
    // 12:00:10.359 [main] INFO  com.itwill.post.dbcp.DbcpTest - ds = HikariDataSource (HikariPool-1) cp 객체 생성 성공. 데이터베이스 연결 풀링 기능을 사용하기 위한 데이터 소스 객체
    // 12:00:10.362 [main] INFO  com.itwill.post.dbcp.DbcpTest - conn = HikariProxyConnection@55755528 wrapping oracle.jdbc.driver.T4CConnection@42e3ede4
    // ㄴ감싸는 ProxyConnection을 close: 반환. T4CConnection@42e3ede4: 실제 connection. pool까지만 반환 
    // "HikariProxyConnection": HikariCP 라이브러리에서 제공하는 커넥션 풀의 커넥션 객체.
    // "oracle.jdbc.driver.T4CConnection": Oracle JDBC 드라이버에서 제공하는 실제 데이터베이스 연결 객체
    //  JDBC 드라이버는 자바 프로그램과 데이터베이스 사이의 통신을 담당하는 드라이버로, 특정 데이터베이스 벤더(여기서는 Oracle)에 대한 특정 기능을 구현하고 제공
    //  HikariCP가 데이터베이스 연결을 관리하는 데 사용되는 프록시 커넥션 객체가, 실제 Oracle 데이터베이스 연결 객체를 래핑(wrapping)하고 있다는 것을 나타내는 정보
    
    @Test // JUnit 단위 테스트 엔진이 실행할 메서드.
    public void testHikariCP() throws SQLException {
        // HikariCP 환경 설정을 위한 객체 생성: 
        HikariConfig config = new HikariConfig();
        
        // HikariCP 환경 설정: 미리 connection을 미리 맺어줌으로써 pool에 connection들을 가지고 있게 됨.
        // 12:00:10.353 [main] INFO  com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection oracle.jdbc.driver.T4CConnection@42e3ede4
        // HikariCP 데이터베이스 연결 풀이 새로운 데이터베이스 연결을 추가했음
        config.setDriverClassName("oracle.jdbc.OracleDriver"); // HikariConfig 클래스가 가지고 있는 setter 메서드. JDBC 드라이버(라이브러리)이름
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe"); // connection을 맺기 위한 url. DB 접속 URL
        config.setUsername("scott"); // DB 접속 계정
        config.setPassword("tiger"); // DB 접속 비밀번호
        
        // 환경 설정을 갖는 Connection Pool(Data Source) 객체 생성
        HikariDataSource ds = new HikariDataSource(config); //  환경설정한 생성된 config를 가지고 객체 생성. 환경설정 정보가 있어야 연결을 맺을 수 있을 것. 
        
        // ds가 null이 아니면 단위 테스트 성공, 그렇지 않으면 실패.  
        Assertions.assertNotNull(ds);
  
        log.info("ds = {}", ds); // string.format이랑 비슷함. 정수, 문자열, 객체 구분 없이 {}만 사용하면, 뒤쪽 아규먼트를 이용한 템플릿. 
        // log4j(pom.xml 세팅하나)로 sysout 같은 번거로움 제거. 
        
        // Connection Pool(Data Source)에서 Connection 객체를 빌려옴.
        Connection conn = ds.getConnection(); // test니깐 throw 
        
        // conn이 null이 아니면 단위 테스트 성공, 그렇지 않으면 실패.
        Assertions.assertNotNull(conn);
        log.info("conn = {}", conn);
        
        // 사용했던 Connection 객체를 Pool에 반환. 물리적인 해제가 아닌 빌려왔던 connection pool에 반환하는 것. 
        conn.close(); // 데이터베이스 서버와의 접속을 물리적으로 끊는 게 아님.
        log.info("conn 반환 성공");
        
    }
}
