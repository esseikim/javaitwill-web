package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, toString, equals, hashCode
@AllArgsConstructor  // 모든 필드를 아규먼트로 갖는 생성자
@Builder
public class PostDetailDto { //DB의 Post 타입을 Dto로 변환 

    private long id;
    private String title;  
    private String content;
    private String author;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private long replyCount; // jsp에서 el로 사용
    
    // Post 타입 객체를 PostDetailDto 타입으로 변환해서 리턴. static: Dto가 만들어지기 전에 메서드 호출. from: DB로부터 가져옴
    public static PostDetailDto fromEntity(Post entity) {
        return PostDetailDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .createdTime(Timestamp.valueOf(entity.getCreated_time()))
                .modifiedTime(Timestamp.valueOf(entity.getModified_time()))
                .build();
    }
}
