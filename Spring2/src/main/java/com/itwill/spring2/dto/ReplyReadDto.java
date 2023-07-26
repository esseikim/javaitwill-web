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
@Getter // private 필드를 읽음
@Setter
@ToString

// 빌더 타입을 만들기 위한 @. DB에서 읽은 Domain을 Dto로 변환
@Builder    //-> @AllArgsConstructor 필수. DB -> DTO
@AllArgsConstructor
public class ReplyReadDto {
     
    // jsp에서 el로 사용
    private long id;
    private long postId;
    private String replyText;
    private String writer;
    private Timestamp modifiedTime;
    
    // Reply 타입 객체를 PostReadlDto 타입으로 변환해서 리턴. static: Dto가 만들어지기 전에 메서드 호출. from: DB로부터 가져옴
   // Service에서 이용할 것.
    
    // static: 맴버 변수가 만들어지기 전에 dto 타입의 변수를 만들기 위해
    // DB에서 select한 Reply 타입 객체를 ReplyReadDto 타입 객체로 변환해서 리턴.
    public static ReplyReadDto fromEntity(Reply entity) {
        return ReplyReadDto.builder()
                .id(entity.getId())
                .postId(entity.getPost_id())
                .replyText(entity.getReply_text())
                .writer(entity.getWriter())
                .modifiedTime(Timestamp.valueOf(entity.getModified_time()))
                .build();
    }
}
