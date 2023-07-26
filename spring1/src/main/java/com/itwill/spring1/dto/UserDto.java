package com.itwill.spring1.dto;

import lombok.Data;

// DTO(Data Transfer Object): 계층 간의 데이터 전달을 위한 객체
// 메서드 호출 시 아규먼트 타입으로 클래스 타입을 넘김
// 즉, 메서드의 아규먼트 / 리턴 타입으로 사용하려는 객체
// DispatcherServlet <=== Data ===> Controller 
// Controller <=== Data ===> Service (Layer)

/*
 *  VO(Value Object): 값을 저장(DB)하기 위한 객체.
 *  보통 Model 클래스를 의미함
 *  데이터 베이스의 테이블 구조를 자바 객체로 표현한 객체.
 *  VO와 DTO가 가지고 있는 멤버변수가 같을 시 DTO 만들지 않아도 됨.
 *  software 설계하기 나름. 용도가 다르므로 따로 만들기도 함. 
 *  
 */

// 롬봇 패키지의 Data: @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 자동 생성
@Data
public class UserDto {
 // form에서 전달한 데이터(요청파라미터 값들)를 저장하기 위한 클래스. 
 // 요청 파라미터: 전부 String. 이었지만 자바의 int, String 타입으로 바꾸고 싶을 뿐. form(ex1.jsp)의 내용과 동일하게 변수 선언
    
    // 변수 이름 잘 쓰기. 테이블의 변수에 맞게/ request Parameter에 맞게.
    private String username;
    private int age;

  // 기본생성자로 UserDto 생성 후(), public으로 공개된 getter, setter 메서드로 값을 저장
   // 요청 파라미터 이름과 setter메서드를 비교함. -> set + 요청 파라메타 -> 호출함.
   

}
