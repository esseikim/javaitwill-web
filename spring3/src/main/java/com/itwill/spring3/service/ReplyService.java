package com.itwill.spring3.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.reply.ReplyCreateDto;
import com.itwill.spring3.dto.reply.ReplyUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.post.PostRepository;
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.repository.reply.ReplyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor  // final field initialize
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;  // postId로 post를 찾기 위함. 
   
    @Transactional(readOnly = true) // entity를 가지고 오는 함수에서는 속도차이가 있을 수 있음. 
    public List<Reply> read(Long postId){
        log.info("read(postId={})", postId);
        
         // 1. postId로 Post를 검색. 
         // 클라이언트로부터 전송된 포스트 아이디 long 값을 통해 해당 post를 찾고, 
         // 필드명 동일하게, repository 메서드 호출  
            Post post = postRepository.findById(postId).orElseThrow();
        
         // 그 entity 객체를 가지고서 reply를 검색.
         // 2. 찾은 Post에 달려 있는 댓글 목록을 검색.
            List<Reply> list = replyRepository.findByPostOrderByIdDesc(post);
            
            return list;
    }
    // postId:실존 필드명이 아님. 커스텀 -> PathVariable과만 동일시. {}: 클라이언트(js)와 서버(servlet)를 맞출 필요는 없음. 
    // 기존 메서드 이용 시 값만 넘겨주면 됨. 
    // 주의: repository에서 메서드 생성 시 where절: 반드시 실존 필드명, 파라미터 실존 필드명 지정, 둘을 일치시켜야 함 
    
    
    // ------------------------------------------------------
    // REPLIES 테이블에서 해당 포스트에 달린 댓글 개수를 검색.
    // List<Reply> replyList = replyService.read(post);
    // model.addAttribute("replyCount", replyList.size());    
    
    // PostController에서 포스트 id값으로 id에 해당하는 post를 찾고, 
    // post의 long postId를 이용해 댓글 리스트를 찾는 방법 
    // replyService.read(post): list의 length 이용 -> 댓글 개수 
    
    // 전체를 포함해서 설정한 post와 부분필드인 post를 다르게 보나봄..
    // Many to One의 관점에서 다시 생각해보기..이 관점이 아니라.. post를 어떻게 채워야 할 지 모르는 듯. (Long postId) 값은 Post 객체의 필드인 id로 인식은 하는 것 같음  
    @Transactional(readOnly = true)
    public List<Reply> read(Post post){
        log.info("read(Post={})", post);

        List<Reply> list = replyRepository.findByPostOrderByIdDesc(post);
        
        return list;
    }
    // List<Reply> replyList = replyService.read(post); <- PostController 
    // ReplyRepository
	// Post id로 검색하기:
    // List<Reply> findByPostId(Long postId);  
    // 찾았지만, 중간에 Reply의 필드 post값이 채워지지 않아 실패. 필드명과 동일하게 해야함. Reply 앤터티에서의 postId의 실제 필드명: post 
    // 즉, Post 객체가 포스트아이디에 해당하는 id 필드를 가지고 있으니, 그 아이디 값을 이용해서 댓글을 찾을 수 있지 않을까? 
    // Post 엔터티의 id는 인식하지만, Reply의 필드인 post는 채우지 못해 실패 
    // -------------------------------------------------------------------
   

    public Reply create(ReplyCreateDto dto) { // title, content, author 
        log.info("create(dto={})", dto);
        
        // 1. Post 엔터티 검색. 고유 포스트(PostId)를 먼저 찾음. 
         Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        
        // 2. ReplyCreateDto 객체를 Reply 엔터티 객체로 변환.
         Reply entity = Reply.builder()
                 .post(post)
                 .replyText(dto.getReplyText())
                 .writer(dto.getWriter())
                 .build();
         
        // 3. DB replies 테이블에 insert
        replyRepository.save(entity);   // dto -> entity(reply)로 변환해야 save 메서드 사용가능. 

        return entity;
        
        /* 
        Dto를 엔터티 객체로 변환해서 리턴하는 메서드:
        public Post toEntity() {
                return Post.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build();
        }
        */
    }

    public void delete(long id) {
        log.info("delete(id={})", id);
        
        // DB replies 테이블에서 ID(고유키)로 엔터티 삭제하기: 
        replyRepository.deleteById(id);
    }

    @Transactional     
    // -> DB에서 검색한 엔터티를 수정하면, 트랜잭션이 끝(메서드 끝)나는 시점에 update 쿼리가 자동으로 실행됨.  
    public void update(Long id, ReplyUpdateDto dto) { // private String replyText;

        log.info("update(id={}, dto={})", id, dto);
        
        
        
        // 실제로 존재하는 entity의 경우에는 오류를 날리지 않고 있다고 알림.
        // PostUpdateDto와는 달리 ReplyUpdateDt 필드엔 id값 부재 
        // -> id로 Post 엔터티 검색햇던 이전과는 달리, pathVariable로 전달 받음
        // pathVariable로 전달받은 id값을 아규먼트로 설정해 해당 Reply를 찾고, 업뎃 필드 setting(title, content)
        // 업데이트 할 필드만 따로 dto로 설정. 업데이트 값만 기존의 Reply 엔터티에 setting 
        // 1. 댓글 아이디로 DB에서 엔터티를 검색(select):
        Reply entity = replyRepository.findById(id).orElseThrow();  // 검색한 결과 없을 시 exception 발생
        
        // 2. 검색한 엔터티의 프로퍼티를 수정
        entity.update(dto.getReplyText());
    
        // 트랜잭셔널 사용하지 않으려면, DB에 save()까지 해줘야 함. 
    
        /* 
        setter가 없어서 만듦. 
        댓글 내용을 수정하고, 수정된 엔터티를 리턴하는 메서드:
        public Reply update(String replyText) {
            this.replyText = replyText;
            return this; 
        }
        */
    
    }
    
    // 해당 post의 댓글 개수 count
    public Long countByPost(Post post) {
        log.info("countByPost(post={})", post);
        
        return replyRepository.countByPost(post);
    }

    /*
    PostController에서 ReplySerivce 이용
    고유한 Post 엔터티를 찾고, entity를 이용해 고유 포스트 해당 댓글 count  
    @PreAuthorize("hasRole('USER')")
    @GetMapping({"/details", "/modify"})  // 쿼리스트링의 경우 아규먼트로 전달 받아야 함
    public void read(Long id, Model model) {
        log.info("read(id={})", id);
        
        // POSTS 테이블에서 id에 해당하는 포스트를 검색. 
        Post post = postService.read(id);
        
        // 결과를 model에 저장 -> 뷰로 전달됨. 
        model.addAttribute("post", post);
        
        // REPLIES 테이블에서 해당 포스트에 달린 댓글 개수를 검색.
        // List<Reply> replyList = replyService.read(post);
        // model.addAttribute("replyCount", replyList.size());

        long count = replyService.countByPost(post);
        model.addAttribute("replyCount", count);
        
        // 컨트롤러 메서드의 리턴값이 없는 경우(void인 경우),
        // 뷰의 이름은 요청 주소와 같다!
        // details -> details.html, modify -> modify.html
    }
    */
    
}
