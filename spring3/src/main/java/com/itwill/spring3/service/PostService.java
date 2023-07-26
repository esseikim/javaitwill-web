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
@RequiredArgsConstructor // final로 선언된 필드의 자동 초기화. final: 생성자의 아규먼트로 반드시 필요.  
@Service  // 스프링에서 관리하는 Service. 자동으로 객체 생성, 싱글톤으로 관리, 필요한 클래스에 주입
public class PostService {
    
    //  생성자를 사용한 의존성 주입: 
    private final PostRepository postRepository; // 의존성 주입, 싱글톤으로 관리 
    
    // DB POSTS 테이블에서 전체 검색한 결과를 리턴: 
    @Transactional(readOnly = true)
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByIdDesc();
    }

    // DB POSTS 테이블에 엔터티를 삽입(inserts): entity의 변화가 생김
    // Controller에게 보여야함 public 
    public Post create(PostCreateDto dto) {  // title, content, author
        log.info("create(dto={}", dto);
        
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
        
        return postRepository.findById(id).orElseThrow();
    }
    
    public void delete(Long id) {
        log.info("delete(id={})", id);
        
        postRepository.deleteById(id);
    }

    @Transactional 
    // readonly. DB에서 읽어와 엔터티 변경 -> DB도 수정(변경 가능).  true -> DB와 무관한 entity . select 한 내용을 update, delete -> false
    public void update (PostUpdateDto dto){
        log.info("update(dto={}", dto);
        
        // (1) 메서드에 @Transactional 에너테이션을 설정하고,
        // (2) DB에서 엔터티를 검색하고, 검색한 엔터티를 수정하면, 
        // (3) 검색한 엔터티를 수정하면,
        // 트랜잭션이 끝나는 시점에 DB update가 자동으로 수행됨!  
        
        Post entity = postRepository.findById(dto.getId()).orElseThrow(); // (2)
//  
        // DB 테이블에 저장
       entity.update(dto); // (3)
        
        return ;
    }
    
    @Transactional(readOnly = true)
    public List<Post> serch(PostSearchDto dto){
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
