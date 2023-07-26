package com.itwill.spring2.repository;

import java.util.List;

import org.springframework.ui.Model;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.PostListDto;
import com.itwill.spring2.dto.PostUpdateDto;

// application-context.xml에서 scan하는 패키지에 있기 때문에
// 인터페이스를 구현하는 클래스가 MyBatis에 의해 자동으로 만들어짐.
// post-mapper.xml 파일에서 설정된 id와 메서드 이름이 같으면, 
// 해당 id의 SQL 문장을 실행하는 구현 메서드를 만들어줌. 


// src/main/resources/mappers/post-mapper.xml
// namespace: 이름 공간 - com.itwill.spring2.repository.PostRepository
public interface PostRepository {
  
    

    
    // create.  PostCreateDto dto
    // 메서드의 nameSpace, 메서드 전체 이름: com.itwill.spring2.repository.PostRepository.insert
    int insert(Post post); 
    // 메서드의 이름은 post-mapper.xml에서의 id를 그대로 사용하기.

    List<Post>selectOrderByIdDesc();
    
    // modify, detail. long id, Model model
    Post selectById(long id); // 한개만 검색. 
    
    // update. PostUpdateDto dto
    int updateTitleAndContent(Post post); // 실패하면 0 성공하면 1 
    
    int deleteById(long id);
    

    List<PostListDto> selectWithReplyCount();
}
