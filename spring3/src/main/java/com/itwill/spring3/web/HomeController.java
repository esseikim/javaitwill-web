package com.itwill.spring3.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // 로그 출력
@Controller 

public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("home()");
        
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        
        return "/main/index"; // view의 이름. 
    }
}
