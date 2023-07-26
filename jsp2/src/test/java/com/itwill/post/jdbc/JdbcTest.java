package com.itwill.post.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.itwill.post.model.Post;

import oracle.jdbc.OracleDriver;

// JUnit Test(자바 단위 테스트)를 하기 위한 클래스: 개발 단계에서 테스트함. 
// JDBC(Java Database Connectivity) 테스트 - ojdbc11 라이브러리 동작 여부 테스트.
// main 메서드를 만들지 않고, 테스트 메서드를 작성하면,
// junit-jupiter-engine에서 테스트 메서드를 실행함. 

@TestMethodOrder(OrderAnnotation.class) //-> 테스트 메서드 실행 순서를 에너테이션으로 설정함. 
public class JdbcTest {
        // Oracle 데이터 베이스 접속 주소. 우리가 다운받은 오라클 버전. 오라클이 사용하는 port 번호: 1521 <- db로 들어가는 문. 웹서버(tomcat)로 들어가는 문: 8081
        private static final String URL= "jdbc:oracle:thin:@localhost:1521:xe"; 
        // 데이터베이스 접속 계정
        private static final String USER = "scott";
        // 데이ㅓ베이스접속 비밀번호 
        private static final String PASSWORD = "tiger";
        
        // 테스트 메서드 작성:  메인 메서드 만들 필요 없이 단위 테스트로만 실행해주면 됨. 
        //   (0) @Test 에너테이션 사용. 그래야 junit-jupiter-engine이 안다. 테스트인지.
        //   (1) 가시성: public     (2) 리턴타입: void  (3) 파라미터를 갖지 않음. 
        // (1):  junit-jupiter-engine이 우리가 만든 메서드를 실행시키는 것. 공개해야. 
        // (2): 메서드 안에서 모든 테스트하는 일이 끝나야 함. 
        // (3): 테스트를 할 내용이 굉장히 많을 것. 테스트마다 파라미터가 가지각색으로 다르게 되면, 모든 경우의 수를 포함해서 메서드를 만들어야 함.
        // 테스트 성공/실패 여부는 테스트 메서드 작성자가 설정. 메서드 안에서 성공여부를 알 수 있는 코드만 작성해주면 됨.
        // maven에 의해서 라이브러리 성공, 접속 객체 성공 등을 test 
        
        @Test   // 테스트 메서드
        @Order(2) // 2번째로 실행할 테스트 메서드
        public void testSelect() throws SQLException{ // test용이니깐 그냥 throw 할거임. 
            // 1. JDBC 라이브러리를 DriverManager에 등록.
            DriverManager.registerDriver(new OracleDriver()); // 아규먼트로 오라클드라이버를 생성해서 넘김
            System.out.println("JDBC 드라이버 등록 성공"); // 확인 로직 작성. run as > junit test. 클래스이름, 메서드 이름 들어가 있음.  junit 성공 시 초록, 실패 시 빨강.
            
            // 2. 등록된 JDBC 드라이버를 사용해서 데이터베이스 서버에 접속. conn이 null이 아니면 밑의 코드들이 진행됨! 
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); //  throws SQLException
            Assertions.assertNotNull(conn); // 원래의 단위테스트에서 하는 방법.  null이 아님을 주장(assert)함. static 메서드 바로 호출
            //-> conn이 null이 아니면 테스트 성공, 그렇지 않으면 테스트 실패. 성공조건으로 주장하는 것. 단위테스트의 결과가 null이 아니다. 
            System.out.println("conn: " + conn); // conn: oracle.jdbc.driver.T4CConnection@70e9c95d.  @70e9c95d connection 객체가 생성된 주소
            // 문자열 + null ->  null을 출력 <- 성공여부 알 수 없음 
           
            //  PreparedStatement에서 사용할 SQL 문장. POSTS 테이블의 전체 내용을 검색(select)
            String sql = "SELECT * FROM POSTS";
            // 3. Connection 객체를 사용해서 Statement 객체를 생성. 
           PreparedStatement stmt = conn.prepareStatement(sql);
           // 4. Statement를 실행 - 접속된 데이터베이스 서버에 SQL 전송하고 결과를 받음.
           ResultSet rs = stmt.executeQuery();
           
           // 5. 결과 처리: ArrayList<Post>를 생성 
           ArrayList<Post> list = new ArrayList<>();
          while (rs.next()) {  // 그 다음 행이 있으면, iterator 패턴. 
               long id = rs.getLong("ID"); // 테이블에서 사용한 컬럼이름 그대로 작성. 
               String title = rs.getString("TITLE");
               String content = rs.getString("CONTENT");
               String author = rs.getString("AUTHOR");
               LocalDateTime created = rs.getTimestamp("CREATED_TIME").toLocalDateTime(); 
               // timestamp에는 localdatetime으로 형변환 해주는 메서드 존재. getDate, getTimestamp 뿐! sql에 timestamp로 만들었었음.
               LocalDateTime modified =rs.getTimestamp("MODIFIED_TIME").toLocalDateTime();
               Post p = new Post(id, title, content, author, created, modified);
               list.add(p);
           }
           
          // ArrayList<Post>의 원소 개수는 1개임을 주장.
          // (expected: 성공 시 기대하는 값, actual: 실제 코드에서 만들어지는 값) 예상하는 값과 실제 값이 같으면 test 성공. 실패면 fail이 뜸
           Assertions.assertEquals(8, list.size());
           
           // 그 결과를 콘솔창에 출력. 
          for(Post p : list) {
              System.out.println(p);
          }
            
            
          // 6. 데이터베이스와 연결된 접속(사용했던 리소스 해제)을 해제. - 생성된 순서와 반대로 close()호출 //  throws SQLException 
            stmt.close();
            conn.close(); // 메서드의 호출결과가 null이 아니다라는 걸 주장하는 코드를 만들 수 있음.  null이 아닌 객체에서 close() 가능. 
            
            // nullPointerException이라 뜸
            System.out.println("연결 해제 성공.");
            // 메인에서 웹서비스에서 이용되는 모델클래스를 작성하고, 서버에서도 배포됨. 이걸 test에서도 가져다 쓸 수 있음. 
        }
       
        //  테스트 클래스는 하난데, 테스트 메서드 2(select, insert test)개 존재. 일반적으로 쓰여진 순서대로. 메서드의 실행 순서 지정(select->insert)
        @Test //JUnit 엔진에서 호출할 테스트 메서드.    ---> 지워버리면 junitengine이 test 실행하지 않음. 순서 안 바꿔도 됨. 클래스 하나에서 테스트 메서드 하나만 만듦. 
        @Order(1) // 1번째로 실행할 테스트 메서드
        public void testInsert() throws SQLException {
            // Driver 등록 -> Connection 맺기 -> PreparedStatement 만들기 -> execute -> 결과 처리 -> 리소스 해제 
            DriverManager.registerDriver(new OracleDriver());
            System.out.println("JDBC 드라이버 등록 성공"); 
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            final String sql = "INSERT INTO POSTS (TITLE, CONTENT, AUTHOR) VALUES (?, ?, ?)" ; 
            // 문장 만들 때 세미콜론 안 됨!  string.format( %s, %s, %s, 컬럼이름, 컬럼이름, 컬럼이름 )  model에 entity 만들어서 import 해서 바로 가져다 쓸 수 있음. 
            // SYSDATE 자동화 되도록 기본값 설정. ID: 시퀀스 설정. 세가지만 넣어주면 됨. 
            
            PreparedStatement stmt = conn.prepareStatement(sql); // 여기까지 동일
           
            // INSERT할 데이터 설정
            stmt.setString(1, "JUnit Test");  // 오라클에선 index: 1로 시작
            stmt.setString(2, "JUnit 단위 테스트를 사용한 insert 테스트");
            stmt.setString(3, "guest");
            
            int result = stmt.executeUpdate(); // rs 불필요. result set을 대신함. 성공하면 1, 실패하면 0
            
            Assertions.assertEquals(1, result);
            //-> insert 문장의 실행 결과가 1이면 단위 테스트 성공, 그렇지 않으면 실패. x표시 뜸. assertionfilederror. 주장하는 내용이 잘못 됐다. 
            
            stmt.close();
            conn.close();
            

            
        }
       
}
