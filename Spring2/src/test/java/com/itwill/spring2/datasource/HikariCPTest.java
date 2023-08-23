package com.itwill.spring2.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 사용.
@ExtendWith(SpringExtension.class) // Spring Junit 테스트를 실행하는 메인 클래스. 메인메서드를 가짐. 우리가 만든 메서드를 실행해주는 클래스.
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
// 스프링 컨텍스트(application-context.xml) 환경 설정 파일의 경로와 이름, JUnit Test를 위해 xml 설정 정보의 위치를 알림.
public class HikariCPTest {
	// 의존성 주입(Dependency Injection), 제어의 역전(IoC: Inversion of Control): 객체 사용 제어권x, 수동적으로 기다림(스프링이 제어권을 가짐).
	// 전통적인 자바 개발에서는 객체를 사용하는 곳에서 생성자를 호출하고, 메서드를 이용.
	// 스프링에서는 스프링 컨테이너(객체 저장 창고)가 필요한 객체들을 미리 메모리에 생성해 두고(appplication-context.xml, bean으로 설정된 클래스의 생성자 호출),
	// 필요한 곳에서 변수 선언과 애너테이션을 사용하면, 스프링 컨테이너가 필요한 곳에 객체를 주입하는 개발 방식.

	@Autowired // 스프링 컨테이너에서 (생성하고) 관리하는 bean을 변수에 자동 할당.
	@Qualifier("hikariConfig") // id="HikariConfig" 객체 주입. 상속관계에 의해 객체를 찾을 수 없을 때 적용.
	private HikariConfig config;

	@Autowired
	private HikariDataSource ds; // 변수 선언, 값 할당 x, null이 아님. 기본생성자 호출 시 null. > 선언만 하면, 그 변수가 null이 아님을 확인하고, 그 변수로 Connection 맺을 수 있음.
	// HikariDataSource extends HikariConfig, HikariDataSource 역시, HikariConfig(다형성) > @Autowired: 상속관계로 적용x.

	/*
	 * HikariConfig : super class 
				|__ HikariDataSource : sub class 
	 * 다형성(polymorphism) 때문에 HikariConfig 타입에는 HikariConfig 객체와 HikariDataSource 객체를 모두 주입할 수 있다.
	 * application-context.xml에서 설정한 id 값을 이용해 특정 bean을 주입받고자 할 때에는 @Qualifier("id")
	 * 애너테이션을 사용하면 됨.
	 */


	@Autowired // application-context.xml에서 자동 주입.
	private SqlSessionFactoryBean sessionFactory;

	@Test
	public void testSqlSession() {
		Assertions.assertNotNull(sessionFactory); // 의존성 주입 확인.
		log.info("session = {}", sessionFactory);
	}

	@Test
	public void testDataSource() throws SQLException {
		Assertions.assertNotNull(config); // 원래라면, 생성자 호출하면 null. application-context.xml에서 객체 생성.
		log.info("config = {}", config);

		Assertions.assertNotNull(ds);
		log.info("ds = {}", ds);

		Connection conn = ds.getConnection(); // DataSource에서 Connection을 빌려옴.
		Assertions.assertNotNull(conn);
		log.info("conn = {}", conn);

		conn.close(); // 사용했던 Connection을 DataSource에 반환.
		log.info("conn close 성공");

	}
}