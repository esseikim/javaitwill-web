package com.itwill.spring3.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;

// 여러 테이블에서 공통으로 사용되는 생성시간, 수정시간을 프로퍼티로 갖는 객체를 설계: 


@Getter 
// ->  DB에 저장된 값을 읽기 위함.  getter 메서드 자동 생성.

@MappedSuperclass
// -> 다른 도메인(엔터티) 클래스의 상위 클래스로 사용됨. 
// -> 상속하는 하위 클래스는 BaseTimeEntity가 정의하는 컬럼들을 갖게 됨.(DB에)   

@EntityListeners(AuditingEntityListener.class)  // sql 문장 없이도 수정, 작성시간 자동 기록
 //-> main 메서드를 갖고 있는 메인 클래스에서 JPA Auditing 기능이 활성화 되어 있는 경우에 
// -> 엔터티가 삽입/수정되는 시간이 자동으로 기록됨
public class BaseTimeEntity {
    
        // 엔터티 클래스의 필드 이름은
        // 데이터베이스 테이블의 컬럼 이름과 같거나, 컬럼 이름을 camel 표기법으로 변환한 이름으로 작성. 
        // (예) 테이블 created_time - 클래스 createdTime 
    
    
        @CreatedDate // insert 될 때의 시간이 자동으로 기록됨.
        private LocalDateTime createdTime; // 카멜 표기법 / db와 동일한 변수명 
        // -> 엔터티 클래스 필드 이름은 자바의 관습(camel 표기법)으로 작성
        // -> 테이블의 컬럼 이름은 데이터베이스의 관습(snake 표기법, _)을 따름.  <- jpa를 이용할 시 가능. 
        // 마이바티스의 경우 도메인 클래스(entity) 설계 시 db 데이터 명과 동일하게 만들었음. 
        
        @LastModifiedDate // update될 때의 시간이 자동으로 기록됨
        private LocalDateTime modifiedTime; 
}
