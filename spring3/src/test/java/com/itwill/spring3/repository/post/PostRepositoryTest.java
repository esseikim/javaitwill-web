package com.itwill.spring3.repository.post;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.PostUpdateDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostRepositoryTest {

    // db를 사용하기 위한 변수. 의존성 주입. 우리가 직접 객체 생성x, spring framework에서 객체 생성해서 넣어주세요. 
    @Autowired  
    private PostRepository postRepository;
    
    // @Test
    public void testSelectAll() {
        List<Post> list = postRepository.findByOrderByIdDesc(); // 리턴: List<Post>  // findAll();
        for(Post p : list) {
             log.info(p.toString());
        }
    }
    
   //  @Test
    public void testSave() {
        // DB 테이블에 insert할 레코드(엔터티)를 생성:
        Post entity = Post.builder()
                .title("JPA 테스트")
                .content("JPA 라이브러리를 사용한 INSERT")
                .author("admin")
                .build();
        
        log.info("insert 전: {}", entity);  // Post: tostring()를 가짐 ->  {}에 채워짐. 
        // 1. lombok 라이브러리가 toString()를 만들어줄 때 superclass까지 하도록 세팅. 2. getter메서드 이용
        log.info("created: {}, modified: {}"
                , entity.getCreatedTime(), entity.getModifiedTime());
        
        // DB 테이블에 insert 
        postRepository.save(entity); // insert할 내용을 넘겨주면 save 후 리턴타입: insert한 내용 
        // -> save 메서드는 테이블에 삽입할 엔터티를 파라미터에 전달하면, 
        // 테이블에 저장된 엔터티 객체를 리턴. 
        // -> 파라미터에 전달된 엔터티 필드들을 변경해서 리턴 -> 리턴값을 저장할 필요 없음. 
        
        log.info("insert 후: {}", entity);
        log.info("created: {}, modified: {}"
                , entity.getCreatedTime(), entity.getModifiedTime());
    }
    
   // @Test  // id 값 검색 후 업데이트. id 검색: where로 id 검색 (null or NN -> jpa 라이브러리: Optional 타입으로 선언)
    public void update() {
        // 업데이트하기 전의 엔터티 검색:
        Post entity = postRepository.findById(62L)
                .orElseThrow(); // crudRepository, Optional ->Post
        
       log.info("update 전: {}", entity);
       log.info("update 전 수정 시간: {} ", entity.getModifiedTime());
       
       // 엔터티를 변경할 내용을 가지고 있는 객체 생성: 
       PostUpdateDto dto = new PostUpdateDto();
       dto.setTitle("JPA update 테스트");
       dto.setContent("JPA Hibernate를 사용한 DB 테이블 업데이트");
       
       // 엔터티를 수정: 
       entity.update(dto);
       
       // DB 테이블 업데이트:
       // JPA에서는 insert와 update 메서드가 구분되어 있지 않음. 
       // save() 메서드의 아규먼트가 DB에 없는 엔터티이면 insert, DB에 있는 엔터티이면 update를 실행. 
       postRepository.saveAndFlush(entity);  // 변경사항 바로 commit. save() <- 시간 차를 두고 커밋 
    }
    
    @Test
    public void testDelete() { 
       long count = postRepository.count();
       // Returns:the number of entities. DB 테이블의 행의 개수(엔터티 개수)
       log.info("삭제 전 count = {}", count);
       
       postRepository.deleteById(62L);
       // 리턴 타입: void, 결과 저장 불가 
       
       count = postRepository.count(); 
       log.info("삭제 후 count ={}", count);
    }
}
