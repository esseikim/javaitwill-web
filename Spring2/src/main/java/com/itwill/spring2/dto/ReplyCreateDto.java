package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor  // 잭슨 라이브러리가 객체 생성 가능
@Getter // 잭슨 라이브러리가 값을 읽게 됨
@Setter // 클라이언트에서 온 값을 세팅
@ToString
public class ReplyCreateDto {
        private long postId; // 댓글이 달린 포스트 아이디
        private String replyText; // 댓글 내용
        private String writer; // 댓글 작성자 아이디
        
        // ReplyCreateDto 타입의 객체를 Reply 타입으로 변환해서 리턴하는 메서드
        public Reply toEntity() {
            return Reply.builder()
                    .post_id(postId)
                    .reply_text(replyText)
                    .writer(writer)
                    .build();
        }
}
