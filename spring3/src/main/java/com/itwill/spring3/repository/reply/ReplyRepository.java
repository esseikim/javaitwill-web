package com.itwill.spring3.repository.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.spring3.repository.post.Post;

public interface ReplyRepository extends JpaRepository<Reply, Long> { // entity 클래스, id의 타입

	// where == By~: 뒤에 있는 글은 Reply의 필드 값(컬럼 이름)이어야 하며, 
    // 만약 값이 Title일 경우 Param도 같은 걸로 해야 함.
	// findBy(규칙) + 변수이름
	
	// Post id로 검색하기:
    // 찾았지만, 중간에 Reply의 필드 post값이 채워지지 않아 실패.
    // List<Reply> findByPostId(Long postId);
	
	
    // 찾은 Post에 달려 있는 댓글 목록을 검색.
	// Post(id)로 검색하기; 
    List<Reply> findByPostOrderByIdDesc(Post post);  
    
    
    // Post에 달린 댓글 개수: countBy + 변수이름
    Long countByPost(Post post);
}
