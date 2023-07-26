package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(  // context 파일을 읽음
        locations = {"file:src/main/webapp/WEB-INF/application-context.xml"}
        ) 
public class ReplyRepositoryTest {
    // Repository 객체를 주입받음(의존성 주입, DI)
    @Autowired
    private ReplyRepository replyRepository;
    // 인터페이스를 구현한 클래스가 만들어져있다. MapperProxy@75961f16
    // replyRepository: MyBatis프레임 워크에 의해 구현되어져 null이 아닌 객체 

    // test class: 기본 생성자만 가짐, test method: 리턴타입 -void, 아규먼트- x
//        @Test
        public void test() {
                Assertions.assertNotNull(replyRepository);
                log.info(replyRepository.toString());
                
                List<Reply> list = replyRepository.selectByPostId(22);
                for(Reply reply : list) {
                    log.info(reply.toString());
                }
        }

//    @Test
    public void testInsert() {
        Reply entity = Reply.builder()
                .reply_text("JUnit test")
                .writer("guest")
                .post_id(48)
                .build();
        int result = replyRepository.insert(entity);
        log.info("result={}", result);

    }
    
//    @Test
    public void testUpdate() {
        Reply entity = Reply.builder()
                .id(2)
                .reply_text("댓글 수정 test")
                .build();
        int result = replyRepository.update(entity);
        log.info("result={}", result);

    }
    
 //   @Test
    public void testDelete() {
        int result = replyRepository.delete(2);
        log.info("result={}", result);
        
    }
//   @Test
    public void testReplyCount() {
        long result = replyRepository.selectReplyCountWithPostId(22);
        log.info("result={}", result);
       
    }
    
    
}



