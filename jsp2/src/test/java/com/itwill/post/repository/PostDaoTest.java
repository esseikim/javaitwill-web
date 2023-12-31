package com.itwill.post.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;

public class PostDaoTest { // DB 연결, dao! Service: 테스트 메서드 가져다 쓰듯이 여기 service에서 작성한 메서드를 호출하면 됨. 
						  // Service는 또 Controller와 연결됨.

	private static final Logger log = LoggerFactory.getLogger(PostDaoTest.class);

	@Test
	public void testSelect() {
		PostDao dao = PostDao.getInstance();
		List<Post> list = dao.select();
		Assertions.assertNotEquals(0, list.size());

		for (Post p : list) {
			log.info(p.toString()); // log.info(): Object를 아규먼트로 갖지 않음. 문자열만 가짐. > toString 명시적 호출.
		}
	}
}
