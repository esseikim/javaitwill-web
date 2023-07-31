package com.itwill.post.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;
import com.itwill.post.repository.PostDao;

// Dao가 DB에서 셀렉트 하는 걸(결과: result set) service로 줄 것. service가 dao가 가진 select라는 메서드를 호출하면, 그 결과가 arraylist로 리턴될 것. 
// -> 서비스는 dao를 멤버변수로 가지고 있어야 select 메서드를 호출 가능함. 

// Service(Business) layer(서비스/비지니스 계층)
// Repository 계층의 메서드들을 사용해서 서비스를 만듦. 여러개의 메서드(dao)를 사용해서 서비스 만듦. 여기도 싱글톤 패턴으로 작성
public class PostService {
    
    // Slf4j 로깅 기능 사용:
    private static final Logger log = LoggerFactory.getLogger(PostService.class); // 해당 클래스
    // 로그 출력: log 객체를 이용해서. 
    
    private static PostService instance = null;
    // Service 계층에서는 Repository 계층의 메서드 사용: dao 사용 완료!!
    private final PostDao postDao = PostDao.getInstance();
    
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
        
        return postDao.select(); // service가 호출 -> dao가 db와 연결해 rs를 주고 응답함. controller도 마찬가지로 service 호출하면 됨. controller: jsp에 주면 됨. 
    }

    public int create(Post post) {
        log.info("crate({})", post);  //{}: 템플릿. 전달받은 아규먼트 확인. 
        return postDao.insert(post); // 셀렉트를 거꾸로 하는 중. form에서 작성했다, t,c,a 리퀘스트 보냄. controller가 서비스 호출, 서비스가 다오 호출. 
    }

    public Post read(long id) { // 하나 읽기 리턴타입: 오버로딩과 관련없다. 
        log.info("read({})", id); // 최종적으로 db랑 연결되어있어서 리턴값이 나옴. 
        return postDao.select(id);
    }

    public int delete(long id) {
        log.info("delete(id={})", id);
        return postDao.delete(id);
    }

    public int update(Post post) {
        log.info("update({})", post);

        return postDao.update(post); // service에서 dao 호출
    }

    public Post serchByValues(String category, String keyword) {
        log.info("serchValues({})", category, keyword);
        return postDao.searchValues(category, keyword);
    }

    
    // test 클래스 따로 만들지 않고 얘는 컨트롤러에서 테스트할 것. 
    /*
     * 15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.controller.post.PostListController - doGet()
     * tomcat이 서블릿 클래스(controller) 찾아서 doget 호출 
     * 15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.service.PostService - read()
        15:15:27.361 [http-nio-8081-exec-3] INFO  com.itwill.post.repository.PostDao - select * from POSTS order by ID desc <- 다오에서 실행. 다오에서 리스트 3개짜리 행 리스트 리턴
     * 서비스에서 목록 읽어올게요 메서드 호출 -> dao가 db에서 직접 가지고와서  list 리턴해줌
     * 15:15:28.127 [http-nio-8081-exec-3] INFO  com.itwill.post.repository.PostDao - # of rows = 8

     */
}
