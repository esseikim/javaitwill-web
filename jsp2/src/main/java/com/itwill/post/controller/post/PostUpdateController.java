package com.itwill.post.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;
import com.itwill.post.service.PostService;

/**
 * Servlet implementation class PostUpdateController
 */
@WebServlet(name = "postUpdateController", urlPatterns = {"/post/update"}) // update 요청이 들어왔을 때 그 요청을 처리하는 Controller.
public class PostUpdateController extends HttpServlet {
private static final long serialVersionUID = 1L;
private static final Logger log = LoggerFactory.getLogger(PostDeleteController.class);
private final PostService postService = PostService.getInstance();  // Controller는 Service 사용.
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("doPost()");

	    // 요청 파라미터의 값(id, title, content)들을 찾음. modify.jsp의 form에서 submit(요청) -> modify.jsp의 name 이용해 js, Controller 작성.
	    // 클라이언트에서 보낸 요청 파라미터의 값을 찾는 방법: getParameter: 서버에서는 Get 방식, Post 방식 구별하지 않음.
	    long id = Long.parseLong(request.getParameter("id")); // name으로 요청 파라미터의 값을 찾음. 
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	
	    Post post = new Post(id, title, content, null, null, null); // id, title, content, author, createdTime, modifiedTim. null: 불필요. time은 자동으로 DB에서 업데이트 됨. 
	    log.info("수정 포스트 = {}", post);
	    
	    // 서비스 계층의 메서드 호출해서 DB에 업데이트 되도록 함. update의 결과: int
	    int result  = postService.update(post);
	    log.info("포스트 업데이트 결과 = {}", result); // PostUpdateController - 포스트 업데이트 결과 = 1
	    
	    // 수정 후 상세보기 페이지로 redirect
	    response.sendRedirect("/post/post/detail?id=" + id); // contextroot부터 써줘야 함. response
	    // cf) PostDetailController - doGet(), request.getRequestDispatcher("/WEB-INF/post/detail.jsp").forward(request, response);   // request 뷰로 포워드(jsp로 감). 
          
          
	/*  
	    17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.controller.post.PostDetailController - id = 24
        17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.service.PostService - read(24)
        17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.repository.PostDao - select(id=24)
        17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.repository.PostDao - select * from POSTS where ID = ? */
	
	    // @WebServlet: context root 제외("" -> post)
	   	// response./request.: context root부터 표기 -> "contextroot/목록보기/해당포스트"("/post/post/detail?id=24") 
	}
}