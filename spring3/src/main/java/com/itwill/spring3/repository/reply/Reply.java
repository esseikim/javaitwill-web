package com.itwill.spring3.repository.reply;

import com.itwill.spring3.repository.BaseTimeEntity;
import com.itwill.spring3.repository.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = {"post"}) // 포함하지 않을 문자열을 배열로 적용 
@NoArgsConstructor  // (1) 필수 

@Getter // (2) 

@AllArgsConstructor
@Builder

@Entity // every '@Entity' class must declare at least one '@Id' 
@Table(name = "REPLIES") // entity와 table명이 다를 경우
@SequenceGenerator(name ="REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ", allocationSize = 1) 
//  id는 시퀀스를 이용함. name: 변수명, 실제 DB의 시퀀스명 
public class Reply  extends BaseTimeEntity{ 
    
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "REPLIES_SEQ_GEN")   // id: 자동 증가
    private Long id; // Primary key
    
 // Fetch: DB에서 데이터를 가져옴
 // EAGER(기본값): 즉시로딩, LAZY: 지연로딩    
    @ ManyToOne(fetch= FetchType.LAZY) // 클래스 타입 -> 관계를 맺어줘야 함. Post 한개에 Reply 여러개가 달릴 수 있음. (앞: 엔터티, 뒤:변수)
                              // 여러개(Many)의 댓글이 한 개(One)의 포스트에 달려 있을 수 있음. 
    private Post post; // foreign key, 관계를 맺고 있는 엔터티. Post에 Reply가 달림
    
    @Column(nullable = false) // 제약조건 줄 때. Not Null
    private String replyText;
    
    @Column(nullable = false)
    private String writer;
    
    // 댓글 내용을 수정하고, 수정된 엔터티를 리턴하는 메서드:
    public Reply update(String replyText) {
        this.replyText = replyText;
        
        return this; 
    }
    
    
    
} 
