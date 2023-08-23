package com.itwill.spring2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.PostCreateDto;
import com.itwill.spring2.dto.PostDetailDto;
import com.itwill.spring2.dto.PostListDto;
import com.itwill.spring2.dto.PostUpdateDto;
import com.itwill.spring2.repository.PostRepository;
import com.itwill.spring2.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// application-context.xml, <context:component-scan> 설정 > com.itwill.spring2.service 패키지와 그 하위 패키지를 @ 스캔.
@Service // -> 스프링 컨테이너에서 서비스 컴포넌트 객체를 생성하고 관리 > 필요한 곳에 주입.
@RequiredArgsConstructor // 2. (2) final로 선언된 필드를 초기화하는 생성자.
@Slf4j // 로그 출력.
public class PostService {

	// 의존성 주입(DI: Dependency Injection):
	// 1. 필드에 의한 의존성 주입 - @Autowired 에너테이션 사용.
	// 2. 생성자에 의한 의존성 주입.
	// (1) 필드를 final로 선언 (2) final 변수를 초기화 할 수 있는 생성자를 작성.

//  @Autowired private PostRepository postRepository;  1. 필드에 의한 의존성 주입
	private final PostRepository postRepository; // 2. (1) 생성자에 의한 의존성 주입, 만들어진 객체에서 메서드 호출. Service: Repository(dao)를 사용.
	private final ReplyRepository replyRepository; // 댓글 개수

	// 포스트 목록 페이지
	public List<PostListDto> read() {
		log.info("read()");
		return postRepository.selectWithReplyCount();

//        List<Post> list = postRepository.selectOrderByIdDesc();  
//        List<PostListDto> result = new ArrayList<>();
//        for (Post p : list) {
//            PostListDto dto = PostListDto.fromEntity(p);
//            result.add(dto);
//        }
//        return result;
//        
//        return list.stream().map(PostListDto::fromEntity).toList(); 
		  // 람다표현식: argument(Post)와 리턴타입(PostListDto)
		  // PostListDto 객체에 있는 메서드(좌우 위치 변경 가능).

		  // list의 원소들을 컬렉션의 스트림을 통해 전달, 하나씩 꺼내서 매핑.
		  // Post 타입의 객체(첫번째 객체부터)가 fromEntity()의 argument로 들어감.
		  // 리턴: PostListDto가 만들어짐. 이 PostListDto가 list에 들어감.
		  // 하나씩 꺼냄 a-> b. Post를 PostListDto로 매핑한다.
		  // 익명 내부 클래스: 람다 표현식, list가 하나씩 PostListDto로 넘어감. 반복문과 동일.
	}

	
	// 새 포스트 작성 페이지, 성공/실패 여부 리턴.
	public int create(PostCreateDto dto) {
		log.info("create({})", dto);

		// PostCreateDto 타입을 Post 타입으로 변환(DB Insert), 리포지토리 계층의 메서드를 호출.
		return postRepository.insert(dto.toEntity());
	}
	
	
	// 포스트 상세보기 페이지.
	public PostDetailDto read(long id) {
		log.info("read(id={})", id);

		// DB POSTS 테이블에서 검색.
		Post entity = postRepository.selectById(id);

		// 검색한 내용을 DTO로 변환.
		PostDetailDto dto = PostDetailDto.fromEntity(entity);

		// DB REPLIES 테이블에서 댓글 개수를 검색.
		long count = replyRepository.selectReplyCountWithPostId(id);
		dto.setReplyCount(count);

		return dto;
	}


	// 포스트 업데이트 : 
	public int update(PostUpdateDto post) { // id, content, title.
		log.info("update({})", post);

		return postRepository.updateTitleAndContent(post.toEntity());
	}

	
	// 포스트 삭제
	public int delete(long id) {
		log.info("delete({})", id);

		return postRepository.deleteById(id);
	}
}