package com.itwill.spring2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Reply;
import com.itwill.spring2.dto.ReplyCreateDto;
import com.itwill.spring2.dto.ReplyReadDto;
import com.itwill.spring2.dto.ReplyUpdateDto;
import com.itwill.spring2.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor  // final field를 초기화하는 생성자
@Service // Spring context에 서비스 컴포넌트 객체로 등록.
public class ReplyService {
         private final ReplyRepository replyRepository;  
         // Spring context가 @을 보고 생성 및 초기화 함. -> 생성자에 의한 의존성 주입.
         
         
         // 댓글등록
        public int create(ReplyCreateDto dto) {
            log.info("create(dto={})",dto);
            
               return replyRepository.insert(dto.toEntity());   // dto를 reply 타입으로 변환.
        }

        // 댓글 목록 가져오기
        public List<ReplyReadDto> read(long postId) {
                log.info("read(postId={})", postId);
            
                List<Reply> list = replyRepository.selectByPostId(postId);
                
                return list.stream().map(ReplyReadDto::fromEntity).toList();  
                // ReplyReadDto::fromEnetity    ==    (x) -> ReplyReadDto.fromEntity(x)
                
                // 다음 표기법을 사용할려면 지켜야 하는 규칙.
                // 1. 변경없이 argument를 전달할 경우, argument가 1개만 있을 경우 사용함.
                // 2-1. ReplyReadDto객체에서 메서드 호출이거나 (예) String::length -> (x) -> x.length()
                // 2-2. 메서드가 argument에서 호출하는 리턴값일 경우.  (예) Timestamp::valueOf -> (x) -> {return Timestamp.valueOf(x);}
        }
        
        public int delete(long id){
            log.info("delete(id={})", id);
            return replyRepository.delete(id);
        }
        
        public ReplyReadDto readById(long id) {
            log.info("readById(id={})", id);
            
            Reply entity = replyRepository.selectById(id);
            
            return ReplyReadDto.fromEntity(entity);
        }

        public int update(long id, ReplyUpdateDto dto) {
            log.info("update(id={}, dto={}", id, dto);
            
            Reply entity = Reply.builder()
                    .id(id)
                    .reply_text(dto.getReplyText())
                    .build();
            log.info("entitiy={}", entity);
            
            return replyRepository.update(entity);
        }
}
