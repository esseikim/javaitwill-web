package com.itwill.spring2.repository;

import java.util.List;

import com.itwill.spring2.domain.Reply;

public interface ReplyRepository { // Mapper 이용.

	int insert(Reply reply);

	List<Reply> selectByPostId(long postId);

	Reply selectById(long id); // 댓글 id > 해당 댓글 1개 리턴, cf) showUpdateModal.

	int delete(long id);

	int update(Reply reply); // updateReply.


	// 포스트의 댓글 개수. PostService에서 호출, 포스트 목록페이지.
	long selectReplyCountWithPostId(long postId);

}