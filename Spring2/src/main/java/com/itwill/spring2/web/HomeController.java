package com.itwill.spring2.web;
// HomeController -> index.jsp -> 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 출력을 위한 @ -> lombok 라이브러리가 Logger 객체를 생성, 변수 이름: log 
@Controller  // DispatcherServlet(Spring Component)에게 등록/말함.
public class HomeController {

    @GetMapping("/") // context root로 들어오는 모든 요청주소 처리. // 파일 이름으로 사용할 수 없기에 리턴 값 필요
    public String home() {
        log.info("home()");
        
        return "index";  // home이라고 써도 됨.. -> index 파일이 views에 있으면 됨. 파일 이름 안 써줘도 자동으로 jsp 호출... 자체 객체 내장: index 
    }
}
