package com.itwill.spring2.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 출력을 위한 @ -> lombok 라이브러리가 Logger 객체를 생성, 변수 이름: log 
@Controller  // Spring Component에 등록, DispatcherServlet 
public class HomeController {

    @GetMapping("/") // ContextRoot로 요청주소 처리. 파일 이름으로 사용할 수 없기에 리턴 값 필요.
    public String home() {
        log.info("home()");
        
        return "index"; 
    }
}