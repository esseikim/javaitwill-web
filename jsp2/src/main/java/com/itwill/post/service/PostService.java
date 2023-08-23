package com.itwill.post.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;
import com.itwill.post.repository.PostDao;

// Dao가 DB에서 셀렉트 결과(result set)를 Service로 줄 것. Service가 dao가 가진 메서드를 호출하면, 그 결과가 arraylist로 리턴될 것. 
// -> 서비스는 dao를 멤버변수로 가지고 있어야 select 메서드를 호출 가능함. 

// Service(Business) layer(서비스/비지니스 계층)
// Repository 계층의 메서드들을 이용해 서비스를 만듦. 동일한 레파지토리 이용해야 함. > 마찬가지로 싱글톤으로 작성.
// cf) 컨트롤러는 서비스 이용함. 많은 컨트롤러에서 동일한 서비스를 이용해야함 -> 싱글톤 정의. 

public class PostService {
    
    // Slf4j 로깅 기능 사용:
    private static final Logger log = LoggerFactory.getLogger(PostService.class); 
    // 로그 출력: log 객체를 이용해서. 
    
    private static PostService instance = null;
    private final PostDao postDao = PostDao.getInstance();  // Service 계층에서는 Repository 계층의 메서드 사용: postDao 사용 가능.

    
    private PostService() {
    }
    
    public static PostService getInstance() {
        if (instance == null) {
            instance = new PostService();
        }
        
        return instance;
    }
    
    public List<Post> read(){ // 전체읽기
        log.info("read()");
        
        return postDao.select(); // Service에서 dao 호출 -> dao가 DB와 연결해 rs를 주고 응답함. Controller도 마찬가지로 Service 호출하면 됨. Controller: JSP에 주면 됨.
        						 // 최종적으로 DB와 연결되어 있어서 리턴값이 나올 수 있음. 
    }

    public int create(Post post) {
        log.info("crate({})", post);  // {}: 템플릿, 전달 받은 아규먼트 확인. 
        return postDao.insert(post); // 셀렉트를 거꾸로 하는 중. form 에서 작성했음(t,c,a 리퀘스트 보냄) > controller가 서비스 호출 > 서비스가 다오 호출. 
    }

    public Post read(long id) { 
        log.info("read({})", id); 
        return postDao.select(id);
    }

    public int delete(long id) {
        log.info("delete(id={})", id);
        return postDao.delete(id);
    }

    public int update(Post post) {
        log.info("update({})", post);
        return postDao.update(post); 
    }

    // test 클래스 따로 만들지 않았음. 컨트롤러에서 테스트 예정. 
    /* cf)
     * 15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.controller.post.PostListController - doGet() // tomcat이 서블릿 클래스(controller) 찾고, doGet() 호출. 
     * 15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.service.PostService - read()
     * 15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.repository.PostDao - select * from POSTS order by ID desc <- dao에서 실행. dao: DB에서 직접 가지고 와서 리스트 리턴. 
     * 15:15:28.127 [http-nio-8081-exec-3] INFO  com.itwill.post.repository.PostDao - # of rows = 8
     */
}