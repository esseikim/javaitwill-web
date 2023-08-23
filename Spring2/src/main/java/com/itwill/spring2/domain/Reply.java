package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 댓글 테이블(REPLIES)에 저장되는 레코드(행 1개)를 표현하는 객체. 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

	private long id; // 댓글 아이디(PK).
	private long post_id; // 댓글이 달린 포스트의 아이디(FK).
	private String reply_text; // 댓글 내용 < 오라클 문법 동일하게 작성.
	private String writer; // 댓글 작성자 아이디.
	private LocalDateTime created_time; // 댓글 작성 시간.
	private LocalDateTime modified_time; // 댓글 수정 시간.
	// cf) 대댓글: 컬럼 아이디 추가.

	// 오라클 테이블 레코드 이름 관습에 따라서 필드명 작성.
	// `_`를 하나의 단어로 생각 > 그래서 getter에서 `_`뒤의 글자를 대문자로 변경 안함.
}