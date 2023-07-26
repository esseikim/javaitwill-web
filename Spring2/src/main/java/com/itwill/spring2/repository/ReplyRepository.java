package com.itwill.spring2.repository;

import java.util.List;

import com.itwill.spring2.domain.Reply;

public interface ReplyRepository {
    
    List<Reply> selectByPostId(long postId); 
 // parameter가 reply-mapper.xml의 #{post_id}(== ? )를 채움.
    
    Reply selectById(long id); // 댓글 id를 주면 그 댓글 한 개를 리턴
    
    int insert(Reply reply);
    
    int update(Reply reply);
    
    int delete(long id);
    
    // 포스트에 달린 댓글의 개수를 리턴하는 메서드(Mapper 이용)
    long selectReplyCountWithPostId(long postId);

}
