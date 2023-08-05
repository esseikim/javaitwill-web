package com.itwill.spring3.dto.reply;

import lombok.Data;

/**
 * reply.js에서 Ajax 요청을 보낸 요청 페이로드 데이터의 이름과 동일하게 필드 이름 지정.
 */
@Data
public class ReplyCreateDto {
    private long postId;
    private String replyText;
    private String writer;
    
}
