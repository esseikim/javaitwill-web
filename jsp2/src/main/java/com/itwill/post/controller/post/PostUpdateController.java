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
@WebServlet(name = "postUpdateController", urlPatterns = {"/post/update"}) // update 요청이 들어왔을 때 그 요청을 처리하는 controller
public class PostUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PostDeleteController.class);
    // controller는 service 사용하므로
    private final PostService postService = PostService.getInstance(); 
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("doPost()");

	    // 요청 파라미터의 값(id, title, content)들을 찾음(수정). modified.jsp의 form에서 submit(요청) 함. modified.jsp의 name 이용
	    long id = Long.parseLong(request.getParameter("id")); //  리퀘스트에 있는 파라미터 찾는다. name으로
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	
	    Post post = new Post(id, title, content, null, null, null); // null 부분 불필요. where: 수정x, id에 필요, author: 수정x, time은 알아서 DB에서 업뎃 
	    log.info("수정 포스트 = {}", post); // request 파라미터 잘 찾았나. 
	    
	    // 서비스 계층의 메서드 호출해서 DB에 업데이트 되도록 함. update의 결과: int
	    int result  = postService.update(post);
	    log.info("포스트 업데이트 결과 = {}", result); // PostUpdateController - 포스트 업데이트 결과 = 1
	    
	    // 수정 후 상세보기 페이지로 redirect
	    response.sendRedirect("/post/post/detail?id=" + id); // contextroot부터 써줘야 함. PostDetailController - doGet()
	/*  
	    17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.controller.post.PostDetailController - id = 24
	            17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.service.PostService - read(24)
	            17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.repository.PostDao - select(id=24)
	            17:08:57.371 [http-nio-8081-exec-29] INFO  com.itwill.post.repository.PostDao - select * from POSTS where ID = ?*/
	}

	
}
