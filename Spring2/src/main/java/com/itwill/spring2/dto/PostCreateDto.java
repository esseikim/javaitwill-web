package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.domain.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Getter, Setter, toString, equals, hashCode 생성: 기본생성자 만듦 + @RequiredArgsConstructor fianl로 선언되어 있는 맴버변수만 argument로.
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor  // 모든 필드를 아규먼트로 갖는 생성자.  @Data에서 만든 기본생성자가 사라지고 모든 필드를 argument로 갖는 생성자 만듦. -> lombok도 똑같
@Builder


/*
 * dispatcherServlet이 @Data을 확인을 하고 해당 객체를 생성 및 만듦
 * maven이 컴파일을 할려고 할떄 중간에서 롬봇 라이브러리가 애너테이션을 보고 필요한 코드를 추가함. 
 */
public class PostCreateDto {
    
   // create.jsp의 name 속성과 동일하게 -> Dispatcher Servlet 자동화
   private String title;  
   private String content;
   private String author;
   
   
   // PostCreateDto 타입의 객체를 Post 타입의 객체로 변환해서 리턴.
   // 맴버변수가 만들어진 다음에 호출해야 함.
   // 객체가 가지고 있는 맴버변수로 Post 만듦 -> args가 필요없음.
   public Post toEntity() {  // non static
       // return new Post(0, title, content, author, null, null); <- 아규먼트의 값들이 직관적이지 않음. 
       
       return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
   }
   

}
 