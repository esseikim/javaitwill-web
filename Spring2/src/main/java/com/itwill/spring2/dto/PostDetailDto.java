package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // Getter, Setter, toString, equals, hashCode
@AllArgsConstructor  // 모든 필드를 아규먼트로 갖는 생성자.
@Builder
public class PostDetailDto {

    private long id;
    private String title;  
    private String content;
	private String author;
	private Timestamp createdTime;
    private Timestamp modifiedTime;
    private long replyCount; // JSP에서 EL로 사용.
    
 
    // DB의 Post 타입 객체를 PostDetailDto 타입으로 변환. static: Dto 생성 전, entity로 메서드 호출(DB로부터 가져옴).
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