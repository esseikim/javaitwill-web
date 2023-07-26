package com.itwill.spring3.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring3.dto.ReplyUpdateDto;
import com.itwill.spring3.dto.reply.ReplyCreateDto;
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.service.ReplyService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final 변수 초기화
@RestController   // 일반 controller( return: view의 이름(html)). 뷰의 이름과 상관 없이 클라이언트로 직접 전달되는 데이터)
@RequestMapping("/api/reply")// 요청주소 항상 동일하게 적용
public class ReplyRestController {
        
    private final ReplyService replyService;
    
    // 댓글 목록 불러오기 
    @PreAuthorize(" hasRole('USER') ") 
    @GetMapping("all/{postId}")   // {} : pathvariable
    public ResponseEntity<List<Reply>> all(@PathVariable long postId) {
        log.info("all(postId={})", postId);
        
        List<Reply> list = replyService.read(postId); 
        
        // 클라이언트로 댓글 리스트를 응답으로 보냄. 
        return ResponseEntity.ok(list);   // 클라이언트로 직접 전송(응답이 내려옴)되는 데이터 값(RestController)
    }
    
    // 댓글 작성
    // request body안의 내용을 분석해서 객체로 만들어달라. cf) get 방식: 쿼리스트링에 있음. 
    // ajax에서 post, put, delete: @RequestBody를 써줘야 동작함. 
    // response의 의미: 하나의 데이터. 이 데이터 안에 data 속성이 있음. 
    @PreAuthorize(" hasRole('USER') ") 
    @PostMapping
    public ResponseEntity<Reply> create(@RequestBody ReplyCreateDto dto){
        log.info("Create(dto={})", dto);
        
        Reply reply = replyService.create(dto);
        log.info("reply={}", reply);
        
        return ResponseEntity.ok(reply);
    }
    
    // 댓글 삭제
    @PreAuthorize(" hasRole('USER') ") 
    @DeleteMapping("/{id}")  // pathvariable. 변수 : {}로 묶는다
    public ResponseEntity<String> delete(@PathVariable long id){
        log.info("delete(id= {})", id);
        
        replyService.delete(id);
        
        return ResponseEntity.ok("Success");
       }
    
    // 댓글 업데이트 
    @PreAuthorize(" hasRole('USER') ") 
    @PutMapping("/{id}")     // PathVariable 과는 동일시. {}: 클라이언트와 서버를 맞출 필요는 없음. 
    // url로 댓글의 id와 RequestBody에 객체가 들어있는데 객체모양이 ReplyUpdateDto와 동일하게 생김. 
    // payload와 동일하게 만든 ReplyUpdateDto
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody ReplyUpdateDto dto){
        log.info("update(dto={})", dto);
        
        // DB 업데이트 서비스 메서드 호출
        replyService.update(id, dto);
        return ResponseEntity.ok("Success");
       }
    
}
