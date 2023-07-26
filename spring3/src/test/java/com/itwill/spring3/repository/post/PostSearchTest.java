package com.itwill.spring3.repository.post;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // 로그 사용
@SpringBootTest  // xml 파일 설정 불필요
public class PostSearchTest {

    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void testSearch() {
        List<Post> list = postRepository
               // .findByTitleContainsIgnoreCaseOrderByIdDesc("TE");  // 대소문자 구분 x. contains: argument로 넘겨줌. title, id: 키워드가 아닌 column 
               //   .findByContentContainsIgnoreCaseOrderByIdDesc("테");
               // .findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc("테",  "1");
              .searchByKeyword("테") ; 
                for(Post p : list) {
            log.info(p.toString());
        }
    }
}
