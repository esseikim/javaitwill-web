package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Getter
@ToString // 콘솔 창에 포맷 출력.

@Builder
@AllArgsConstructor
*/

// 댓글 개수 추가 후 만든 것. 
@Builder // builder() > 내부클래스 PostDtoBuilder 클래스 타입을 리턴.
@AllArgsConstructor // field를 전부 arg로 갖는 생성자, builder() 사용.
@NoArgsConstructor // @AllArgsConstructor > 기본 생성자 생성 명시.
@Data
public class PostListDto {
	private long id;
	private String title;
	private String author;
	private Timestamp created_time;
	// jstl의 포맷태그는 LocalDateTime을 처리하지 못함(model/domain에서는 상관 없음).
	// -> JSTL에서는 LocalDateTime 객체를 사용하지 못함 > Timestamp 타입으로 선언(jsp에서 jstl 포맷 태그를 이용하기 위함).
	private long rcnt; // 댓글 개수.

	
	// Post 타입의 객체를 PostListDto 타입의 객체로 변환해서 리턴하는 메서드.
	public static PostListDto fromEntity(Post entity) {

		return PostListDto.builder().id(entity.getId()).title(entity.getTitle()).author(entity.getAuthor())
				.created_time(Timestamp.valueOf(entity.getCreated_time())) // entity.getCreated_time(): LocalDateTime을 리턴함 -> Timestamp 변환.
				.build();
	}
}