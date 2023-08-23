package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder // -> @AllArgsConstructor 필수. DB에서 가져온 Domain(Reply)을 Dto로 변환(DB -> DTO).
public class ReplyReadDto {

	private long id;
	private long postId;
	private String replyText;
	private String writer;
	private Timestamp modifiedTime;
	// cf) reply.js
	// Timestamp 타입 값을 날짜/시간 타입 문자열로 변환:
	// const modified = new Date(reply.modifiedTime).toLocaleString(); // js의 Date 클래스.

	// Service에서 이용할 것. static: 맴버 변수가 만들어지기 전에 dto 타입의 변수를 만들기 위해.
	// DB에서 select한 Reply 타입 객체를 ReplyReadDto 타입 객체로 변환해서 리턴.
	public static ReplyReadDto fromEntity(Reply entity) {
		return ReplyReadDto.builder().id(entity.getId()).postId(entity.getPost_id()).replyText(entity.getReply_text())
				.writer(entity.getWriter()).modifiedTime(Timestamp.valueOf(entity.getModified_time())).build();
	}
}