package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@ExtendWith(SpringExtension.class) // Spring Junit 테스트를 실행하는 메인 클래스. 
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class RepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	public void testDelete() {
		int result = postRepository.deleteById(1);
		Assertions.assertEquals(1, result);
	}

	// @Test
	public void testUpdate() {
		Post post = Post.builder().id(1) // 업데이트 할 포스트 아이디.
				.title("업데이트 TEST") // 업데이트 할 제목.
				.content("MyBatis 프레임워크를 사용할 DB 업데이트") // 업데이트 할 내용.
				.build();
		int result = postRepository.updateTitleAndContent(post); // 성공한 경우 result: 1.
		Assertions.assertEquals(1, result);
	}

	// @Test
	public void selectById() {
		Post result = postRepository.selectById(1);
		Assertions.assertNotNull(result);
		log.info(result.toString());

		result = postRepository.selectById(-1);
		Assertions.assertNotNull(result);
	}

	// @Test
	public void testSelectOrderByIdDesc() {
		List<Post> list = postRepository.selectOrderByIdDesc();
		for (Post p : list) {
			log.info(p.toString());
		}
	}

//   @Test
	public void testPostRepository() {
		Assertions.assertNotNull(postRepository);
		log.info("postRepository = {}", postRepository);

		Post post = Post.builder().title("MyBatis 테스트").content("MyBatis 라이브러리를 이용한 Builder 패턴 구현").author("admin")
				.build();
		log.info(post.toString());

		int result = postRepository.insert(post);
		Assertions.assertEquals(1, result);
		log.info("result = {}", result);
	}
}
