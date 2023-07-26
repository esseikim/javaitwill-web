package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 업데이트 요청에서 전송되는 요청 파라미터를 저장하기 위한 객체
// filed 이름을 선언할 때는 요청 파라미터 이름(name 속성의 값)과 같게.

@Getter
@NoArgsConstructor  // 기본 생성자
@Setter
@ToString


//spring Framework에서 자바 관습을 지키면서 만들어야 함. (getter, setter..)
//-> dispatcherServlet이 클래스를 생성할 떄 가장 먼저 기본 생성자를 생성하고, 그 다음 setter를 설정해줌 
//-> 만약 둘 중 하나라도 없으면 제대로 argument를 넘겨 줄수가 없다.
//+ @AllArgsConstructor: 기본 생성자, setter 대체 가능함
public class PostUpdateDto {

    private long id;
    private String title;  
    private String content;


    // PostCreateDto 타입의 객체를 Post 타입의 객체로 변환해서 리턴. (repository: domain을 이용하기 때문)
    public Post toEntity() {  // non static
        // return new Post(0, title, content, author, null, null); <- 아규먼트의 값들이 직관적이지 않음. 

        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
