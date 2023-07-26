package com.itwill.post.controller.post;
// 순서:  delete(jsp) -> servlet(controller) -> service ->
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.service.PostService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostDeleteController
 */
@WebServlet(name = "postDeleteController", urlPatterns = {"/post/delete"})
public class PostDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private static final Logger log = LoggerFactory.getLogger(PostDeleteController.class);
	// controller는 service 사용하므로
    private final PostService postService = PostService.getInstance(); 
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	            log.info("doPost()");
	            
	            // 요청할 파라미터(무조건 String -> 숫자타입으로 변환) 중에서 삭제할 글 번호를 찾음.
	            // 네트워크 delete 페이로드 : id
	            long id = Long.parseLong(request.getParameter("id"));
	            log.info("id = {}", id);
	            
	            // 서비스 계층의 메서드를 사용해서 포스트를 삭제.  이런 메서드가 이 클래스에 있었음 좋겠다 ~
	           int result = postService.delete(id); 
	            log.info("포스트 삭제 결과 = {}", result); // 리턴결과를 여기서만 봄. PostDeleteController - 포스트 삭제 결과 = 1
	           
	            // 포스트 목록 페이지로 이동 (redirect): PRG 패턴 (post요청, redirect // 브라우저, 클라이언트가 다시한번 get요청을 보냄)
	           response.sendRedirect("/post/post"); // redirect -> 다시 PostListController - doGet()
	           
//	           15:09:40.814 [http-nio-8081-exec-7] INFO  com.itwill.post.controller.post.PostDeleteController - doPost() : post 여기로 post
//	           15:09:40.815 [http-nio-8081-exec-7] INFO  com.itwill.post.controller.post.PostDeleteController - id = 24   <- 파라미터 분석. redirect 
//	           15:09:40.820 [http-nio-8081-exec-8] INFO  com.itwill.post.controller.post.PostListController - doGet()  : 클라이언트가 get 방식의 요청을 다시 보냄  
//	           15:09:40.820 [http-nio-8081-exec-8] INFO  com.itwill.post.service.PostService - read()  : 목록보기 (테이블의 개수만큼 목록을 다시 그려줌- html) 
//	           15:09:40.821 [http-nio-8081-exec-8] INFO  com.itwill.post.repository.PostDao - select * from POSTS order by ID desc
//	           15:09:40.824 [http-nio-8081-exec-8] INFO  com.itwill.post.repository.PostDao - # of rows = 14   
	}

}
