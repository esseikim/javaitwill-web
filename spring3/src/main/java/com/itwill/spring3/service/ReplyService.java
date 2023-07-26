package com.itwill.spring3.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.ReplyUpdateDto;
import com.itwill.spring3.dto.reply.ReplyCreateDto;
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
        
         // 1. postId로 Post를 검색
            Post post = postRepository.findById(postId).orElseThrow();
        
         // 2. 찾은 Post에 달려 있는 댓글 목록을 검색.
            List<Reply> list = replyRepository.findByPostOrderByIdDesc(post);
            
            return list;
    }
    
    // 댓글목록 전체보기 
    @Transactional(readOnly = true)
    public List<Reply> read(Post post){
        log.info("read(Post={})", post);

        List<Reply> list = replyRepository.findByPostOrderByIdDesc(post);
        
        return list;
    }
    
    // 해당 post의 댓글 개수 count
    public Long CountByPost(Post post) {
        log.info("countByPost(post={})", post);
        
        return replyRepository.countByPost(post);
    }

    public Reply create(ReplyCreateDto dto) {
        log.info("create(dto={})", dto);
        
        // 1. Post 엔터티 검색
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
    }

    public void delete(long id) {
        log.info("delete(id={})", id);
        
        // DB replies 테이블에서 ID(고유키)로 엔터티 삭제하기: 
        replyRepository.deleteById(id);
    }

    @Transactional     
    // -> DB에서 검색한 엔터티를 수정하면, 트랜잭션이 끝(메서드 끝)나는 시점에 update 쿼리가 자동으로 실행됨.  
    public void update( Long id, ReplyUpdateDto dto) {
        log.info("update(id={}, dto={})", id, dto);
        
        // 1. 댓글 아이디로 DB에서 엔터티를 검색(select):
        Reply entity = replyRepository.findById(id).orElseThrow();  // 검색한 결과 없을 시 exception 발생
        
        // 2. 검색한 엔터티의 프로퍼티를 수정
        entity.update(dto.getReplyText());
    
        // 트랜잭셔널 사용하지 않으려면, DB에 save()까지 해줘야 함. 
    }

}
