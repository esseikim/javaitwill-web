package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dispatcherServlet: @Data 확인, 해당 객체를 생성. 
// maven 컴파일 시 롬봇 라이브러리가 애너테이션을 보고 필요한 코드를 추가.

@Data // Getter, Setter, toString, equals, hashCode: 기본생성자 + setter.
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 아규먼트로 갖는 생성자. @Data에서 만든 기본생성자가 생성x > 명시.
@Builder

public class PostCreateDto {

	// create.jsp의 name 속성과 동일하게 작성 > Dispatcher Servlet 자동화.
	private String title;
	private String content;
	private String author;

	// PostCreateDto 타입의 객체를 Post 타입의 객체로 변환해서 리턴.
	// 맴버변수가 만들어진 뒤 호출. 객체가 가지고 있는 맴버변수로 Post 객체 생성. -> args 불필요.
	public Post toEntity() { // non static.
		
		// return new Post(0, title, content, author, null, null); < 아규먼트의 값: 직관적x.
		return Post.builder().title(title).content(content).author(author).build();
	}

}