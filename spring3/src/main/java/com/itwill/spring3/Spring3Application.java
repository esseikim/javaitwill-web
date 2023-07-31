package com.itwill.spring3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;


// jpa: 엔터티클래스를 이용해서 db의 정보를 업데이트하고 정보를 가져오는 기능
// post: 도메인, postrepository: repository 계층의 class

@Slf4j
@EnableJpaAuditing  // JPA Auditing 기능 활성화.  BaseTimeEntity 클래스와 연결
@SpringBootApplication
public class Spring3Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring3Application.class, args);
		log.info("main started");
	}

}
