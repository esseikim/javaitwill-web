package com.itwill.post.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceUtil {  
        // singleton 디자인 패턴: 데이터소스 하나만 생성할 수 있도록. 여러개 x
        private static HikariDataSourceUtil instance = null;
        
        private HikariDataSource ds;  // 환경설정, 환경설정 객체, ds가 null이 아님을 확인함 -> ds 데이터 코드를 안심쓰고 쓸 수 있음. 멤버변수 선언. 
            
        private HikariDataSourceUtil() {  // 외부 객체 생성 불가. 멤버변수 초기화
            // HikariCP를 사용하기 위한 환경 설정 객체;
            HikariConfig config = new HikariConfig();
            
            // CP(Data Source)을 생성하기 위한 설정들:
            config.setDriverClassName("oracle.jdbc.OracleDriver");
            config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
            config.setUsername("scott");
            config.setPassword("tiger");
            
            // CP(Data Source) 객체 생성: 
            ds = new HikariDataSource(config);
        }
        
        public static HikariDataSourceUtil getInstance() { // 객체 생성 이전 호출 메서드
                if(instance == null ) {
                    instance = new HikariDataSourceUtil();
                }
                return instance;
        }
        
        public HikariDataSource getDataSource() { // getter 메서드 이름만 바꿔준 것. getDs라 안함. 자기가 가진 ds를 리턴. 
            // 절대로 null이 리턴 될 수 없음. 이 메서드는 static이 아님. 생성자 호출된 후에 호출 가능. config, ds 모두 만들어졌을 것. 
            return ds;
        }
}
