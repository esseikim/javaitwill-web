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
 * Servlet implementation class PostSerchController
 */
@WebServlet(name = "postSerchController", urlPatterns = {"/post/serch"}) // update 요청이 들어왔을 때 그 요청을 처리하는 controller

public class PostSerchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostDeleteController.class);
    // controller는 service 사용하므로
    private final PostService postService = PostService.getInstance(); 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.info("doGet()");
	    // 요청 파라미터의 값들을 찾음. modified.jsp의 form에서 submit(요청) 함. modified.jsp의 name 이용
	          
	    String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");
	    
        Post post = postService.serchByValues(category, keyword);
        request.setAttribute("post", post);
        
        request.getRequestDispatcher("/WEB-INF/post/modify.jsp") // 요청 받고 해당폴더로감, forward방식으로 
        .forward(request, response);  
        

}
}
