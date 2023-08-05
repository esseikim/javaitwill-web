package com.itwill.spring3.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final로 선언된 필드의 자동 초기화. final: 생성자의 아규먼트로 반드시 필요. 변경 불가 
@Service  // 스프링에서 관리하는 Service. 자동으로 객체 생성, 싱글톤으로 관리, 필요한 클래스에 주입
public class PostService {
    
    // 생성자를 사용한 의존성 주입: 
    private final PostRepository postRepository; // 의존성 주입, 싱글톤으로 관리 

    // readOnly = false(기본값): select 과정이 늦을 수도 있음. 변경되고 있는지를 추적하고 관리하며, 변경시 DB와 연동이 됨.
    // readOnly = true: 읽기만 하고, DB에 수정 변경 안됨.
    // 읽기만 하고 수정을 안 하기에 즉, 읽기 전용 용도이기에 Select 속도를 빠르게 하기 위해서 애너테이션 설정.
    // DB POSTS 테이블에서 전체 검색한 결과를 리턴: 
    @Transactional(readOnly = true)
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByIdDesc();
    }
    
    

    // DB POSTS 테이블에 엔터티를 삽입(inserts): entity의 변화가 생김
    // Controller에게 보여야함 -> public 
    public Post create(PostCreateDto dto) {  // title, content, author
        log.info("create(dto={})", dto);
        
        // DTO를 Entity로 변환:
        Post entity = dto.toEntity();
        log.info("entity={}", entity);
  
        // DB 테이블에 저장
        postRepository.save(entity);
        log.info("entity={}", entity);
        
        return entity;
    }
    
    @Transactional(readOnly = true) // entity 변경 x, 읽고 리턴만 하면 됨. 
    public Post read(Long id) {
        log.info("read(id={})", id);
        
        // 실제로 존재하는 entity의 경우에는 오류를 날리지 않고 있다고 알림.
        return postRepository.findById(id).orElseThrow();
    }
    
    public void delete(Long id) {
        log.info("delete(id={})", id);
        
        postRepository.deleteById(id);
    }

    
    @Transactional // (1)
    // readonly. DB에서 읽어와 엔터티 변경 -> DB도 수정(변경 가능).  
    // DB와 무관한 entity -> true
    // select 한 내용을 update, delete -> false
    public void update (PostUpdateDto dto){
        log.info("update(dto={}", dto);
        
        // (1) 메서드에 @Transactional 에너테이션을 설정하고,
        // (2) DB에서 엔터티를 검색하고, 검색한 엔터티를 수정하면, 
        // (3) 검색한 엔터티를 수정하면,
        // 트랜잭션이 끝나는 시점에 DB update가 자동으로 수행됨!  
        
        // 실제로 존재하는 entity의 경우에는 오류를 날리지 않고 있다고 알림.
        // dto의 id로 Post 엔터티 검색, 나머지 필드 setting(title, content)
        // 업데이트 할 필드만 따로 dto로 설정. 업데이트 값만 기존의 Post 엔터티에 setting 
        Post entity = postRepository.findById(dto.getId()).orElseThrow(); // (2) 
     
        // DB 테이블에 저장
        entity.update(dto); // (3)
        
        return ;
        
        /* 
        PostUpdateDto: id, title, conetent 
        setter가 없어서 만듦. 
        Post 엔터티의 title과 content를 수정해서 리턴하는 메서드(setter 메서드 두개의 역할을 함.):
        public Post update(PostUpdateDto dto) {
            this.title = dto.getTitle();
            this.content = dto.getContent();
            
            return this;
        }
        */
    }
    
    
    /*
    // request parameter의 name과 동일하게 작성
    private String type;  // t, c, tc, a,..
    private String keyword; 
     */
    @Transactional(readOnly = true)
    public List<Post> search(PostSearchDto dto){
        log.info("search(dto={}", dto);
        
        List<Post> list = null;
        switch (dto.getType()) { // type
        case "t":
            list = postRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "c":
            list = postRepository.findByContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "tc":
            list = postRepository.searchByKeyword(dto.getKeyword()); // JPQL로 만든 메서드 이용
            break;
        case "a":
            list = postRepository.findByAuthorContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        }
        
        return list;
    }

}
