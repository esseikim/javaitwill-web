package com.itwill.spring3.repository.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.spring3.repository.post.Post;

public interface ReplyRepository extends JpaRepository<Reply, Long> { // entity 클래스, id의 타입


    
    // Post (필드명 <- db: POST_ID)로 검색하기: findBy(규칙) + 변수이름
    List<Reply> findByPostOrderByIdDesc(Post post);  
    
    // Post에 달린 댓글 개수: countBy + 변수이름
    Long countByPost(Post post);
}
