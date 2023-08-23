package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
// Spring Framework: 자바 관습을 지키면서 만들어야 함(getter, setter). > 필드명은 요청 파라미터의 name 속성의 값과 동일하게 작성.
//-> dispatcherServlet의 클래스 생성: 기본 생성자 생성, setter 설정. 
//-> 만약 둘 중 하나라도 없으면 제대로 argument를 넘겨 줄 수 없음.
// @AllArgsConstructor: 기본 생성자, setter 대체 가능함
public class PostUpdateDto {

	private long id;
	private String title;
	private String content;

	// PostCreateDto 타입의 객체를 Post 타입의 객체로 변환해서 리턴.(Repository: Domain을 이용하기 때문).
	public Post toEntity() { // non static

		return Post.builder().id(id).title(title).content(content).build();
	}
}