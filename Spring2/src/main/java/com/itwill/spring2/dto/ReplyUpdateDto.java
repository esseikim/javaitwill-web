package com.itwill.spring2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyUpdateDto {
        private String replyText; // js의 파일에서 사용한 key값과 동일하게 설정
}
