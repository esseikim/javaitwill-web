package com.itwill.spring3.config;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

// application.properties의 텍스트 파일을 제외하곤 xml 파일로 설정 x. 자바 클래스 이용하여 설정
// Spring legacy의 bean 생성: application.xml 
// Spring security filter/DPS는 UserDetailsService을 찾고 사용자 상세정보를 만들어줌
// 그래서 해당 클래스(UserDetailsService)를 상속받아야 그대로 동작이 가능함.

// 상속관계: Bean ->Component -> Cont, Serv, Rev : @ bean을 생성해주는 에너테이션.
// 스프링이 bean을 가지고있다가 필요한 곳에서 쓸 수 있도록 넣어줌
// 빈 생성, 관리. 빈들을 가지고 있는 상자 -> 스프링 컨테이너 

@EnableMethodSecurity // Controller 메서드에서 security 설정
// @EnableWebSecurity  // 우리가 설정한 웹 시큐리티 설정에 따라 웹 시큐리티를 실행시킴. <- /**/ 사용
@Configuration // 객체 생성. 스프링 컨테이너에서 빈(웹 -bean. 자바 -객체)으로 생성, 관리 - 필요한 곳에 의존성 주입
public class SecurityConfig {  
    
        // Spring Security 5 버전부터는 비밀번호는 반드시 암호화를 해야 함. 암호화 하지 않은 비밀번호는 로그인 불가
        // 비밀번호 암호화, 복호화를 해주는 비밀번호 인코더(Password encoder) 객체를 bean으로 생성해야 함. 
        // 비밀번호를 암호화하지 않으면 HTTP 403(access denied, 접근 거부) 또는
        // HTTP 500(internal server error, 내부 서버 오류)가 발생함. 
    
    
        @Bean  // 1. 스프링 컨테이너가 관리하는 객체
        public PasswordEncoder passwordEncoder() {  // 인터페이스는 기본생성자로 안되니 BCryptPasswordEncoder 참고하여 함. 
            return new BCryptPasswordEncoder();  // 2. 암호화가 필요한 곳에 이 객체를 넣어줌. 비밀번호를 문자열로 리턴
        }
        
        
        // 로그인할 때 사용할 임시 사용자(메모리에 임시 저장) 생성
        @Bean
        public UserDetailsService inMemoryUserDetailsService() {
            // 웹서비스를 이용할 수 있는 사용자 상세정보
            //  UserDetails  : DB에 들어가는 유저 정보. 일종의 entity. 그 정보를 DB에서 검색이 아닌 임의로 만든다.   
            UserDetails  user1 = User
                        .withUsername("user1") // 로그인할 때 사용할 사용자 이름
                        .password(passwordEncoder().encode("1111")) // 로그인할 때 사용할 비밀번호
                        .roles("USER") // 사용지 권한(USER, ADMIN,...). USER 권한을 가진 사람들만 접근 가능
                        .build(); //  UserDetails 객체 생성.
                
            UserDetails  user2 = User
                    .withUsername("user2")
                    .password(passwordEncoder().encode("2222")) 
                    .roles("USER", "ADMIN") 
                    .build(); 
            
            UserDetails  user3 = User
                    .withUsername("user3")
                    .password(passwordEncoder().encode("3333"))
                    .roles("ADMIN") 
                    .build();
            
            return new InMemoryUserDetailsManager(user1, user2, user3); 
            // UserDetailsService를 구현하는 클래스: InMemoryUserDetailsManager
            // UserDetailsService: filter가 사용자 정보를 가져오기 위해 사용하는 클래스.  임시로 filter에게 사용자 정보를 제공해주기 위한 클래스
            // 이 클래스를 구현하게 되면, filter가 UserDetailsService를 이용할 수 있음. 
            // 이 클래스는 UserDetails(사용자 정보)를 가지고 있어야 함. 
            // filter: Controller로 가기 전 요청을 가로챔
        }
        
        // Security Filter 설정 bean
        // 로그인/로그아웃 설정
        // 로그인 페이지 설정, 로그아웃 이후 이동할 페이지
        // 페이지 접근 권한 - 로그인해야만 접근 가능한 페이지, 로그인 없이 접근 가능한 페이지 설정
        @Bean  // 스프링컨테이너가 이 메서드 호출하여 Bean 생성, 관리 -> 필요한 곳에 주입
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // CSRF(Cross Site Request Forgery) 기능 활성화하면, 
            // Ajax POST/PUT/DELETE 요청에서 CSRF 토큰을 서버로 전송하지 않으면 403 에러가 발생
            // -> CSRF 기능 비활성화 
          
            http.csrf((csrf) -> csrf.disable());  // 익명함수로 넘겨줘야.(람다표현식) 
            
            // 로그인 페이지 설정 - 스프링에서 제공하는 기본 로그인 페이지를 사용. 
            http.formLogin(Customizer.withDefaults());  // form을 이용해서 로그인함(기본 페이지). Customizer를 이용해 커스텀 페이지 적용 가능. 
            
            // 로그아웃 이후 이동할 페이지: contextroot(메인페이지)
            http.logout((logout) -> logout.logoutSuccessUrl("/")); 
           
            
            // 페이지마다의 접근 권한 설정
            /*
            http.authorizeHttpRequests((authRequest) -> 
            authRequest // 접근 권한을 설정할 수 있는 객체
            // 권한이 필요한 페이지들을 설정
            .requestMatchers("/post/create", "/post/details", "/post/modify", 
                    "/post/update", "/post/delete", "/api/reply/**") // 익명사용자는 댓글 x
            .hasRole("USER") // 위에서 설정한 페이지(요청주소)들이 USER 권한을 요구함을 설정
            .requestMatchers("/**")  // 이외의 모든페이지. 요청주소 /post/create 처럼 /post 뒤의 하위주소가 몇단계가 있어도 됨. 
                                                       // . anyRequest() 와 동일. 어떤 요청이든지.  
            // .authenticated() 인증완료. 권한 여부 상관없이 아이디/비밀번호가 일치하면
            .permitAll()   // 권한없이 접근 허용. 
                    ); // 추상메서드 구현
            */
           // 단점: 새로운 요청 경로, 컨트롤러를 작성할 때마다 Config 자바 코드를 수정해야 함. 
           // -> 컨트롤러 메서드를 작성할 때 애너테이션을 사용해서 접근 권한을 설정할 수도 있음. 
           // (1) SecurityConfig 클래스에서 @EnableMethodSecurity 애너테이션 설정
           // (2) 각각의 컨트롤러 메서드에서 @PreAuthorize 또는 @PostAuthorize 애너테이션을 사용. 
            
            
            return http.build();
        }
        //  CSRF 부연설명
        // 폼에 토큰을 숨겨놓음. 잘못된 경로로 들어온 정보는 토큰과 일치하지 않아, 적용할 수 없음.
        // 모든 폼 양식에 토큰이 숨겨져 클라이언트에 내려감. 
        // 클라이언트는 로그인 시 자기가 받은 토큰을 그대로 서버로 전송해주지 않으면 아이디, 비밀번호가 일치하더라고 로그인 불가
        // js에서 ajax 이용 -> 내부적으로 들어옴. disable()하지 않으면 -> 동작 기능하지 못함
        
        // 스프링 시큐리티 필터에서 사용할 수 있는 환경설정 빈(SecurityFilterChain)을 만들었기에 로그인 없이 홈페이지 들어가기 가능. 
        // 스프링 시큐리티의 원래 설정 적용x
        // 로그인 사용자 권한 설정을 하지 않았기에  =>  페이지 접근 권한 설정 적용하면 됨. 
        // 설정한 정보는 스프링시큐리티필터가 사용함. 
        
        // 자바는 클래스 바깥에서 함수를 정의할 수 없음. 클래스 안의 함수: 메서드만 가지고 있음
        // js: 클래스 안, 바깥 모두에서 함수 정의 가능. 
        // authRequest: 인터페이스 구현 객체
        // 구현해야하는 추상메서드가 하나인 인터페이스: 함수형 인터페이스(functional interface)
        // 인터페이스 구현 = 추상메서드 바디를 만들어줌.  
        
    
}
