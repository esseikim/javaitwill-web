package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor; //@을 통해 필요한 코드 자동 주입. 
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder  // builder() -> static 내부 클래스 생성 - 맴버 변수(Post 클래스의 맴버 변수와 동일)
@Getter
@Setter
@ToString
public class Post {
        
    private long id;
        private String title;
        private String content;
        private String author;
        private LocalDateTime created_time;
        private LocalDateTime modified_time;
        
        
}
