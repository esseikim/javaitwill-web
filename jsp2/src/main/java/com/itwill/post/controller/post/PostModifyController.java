package com.itwill.post.controller.post;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;
import com.itwill.post.service.PostService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostModifyController
 */
@WebServlet(name = "postModifyController", urlPatterns = {"/post/modify"})
public class PostModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	private static final Logger log = LoggerFactory.getLogger(PostModifyController.class);
	private final PostService postService = PostService.getInstance();
	// 컨트롤러는 서비스 이용함. 많은 컨트롤러에서 동일한 서비스를 이용해야함 -> 싱글톤 정의 
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        log.info("doGet()");
	        
	        // 수정할 포스트 번호를 요청 파라미터에서 찾음.
	        long id = Long.parseLong(request.getParameter("id")); // 요청 파라미터에서 id를 찾고, long으로 변환
	        log.info("id = {}", id);
	        
	        // 포스트 번호로 수정할 포스트 내용을 검색. 
	        Post post = postService.read(id);
	        
	        // 포스트 객체를 request의 속성 값으로 설정. request: post라는 변수를 가지며, 그 변수를 jsp가 el로 사용(그대로 forward)
	        request.setAttribute("post", post);
	        
	        // 뷰로 포워드
	        request.getRequestDispatcher("/WEB-INF/post/modify.jsp")
	        .forward(request, response);
	        // 포워드는 서블릿 내에서 처리한 결과를 다른 서블릿이나 JSP로 전달하는 기능.
			// 클라이언트의 요청을 하나의 서블릿에서 처리한 후, 그 결과를 다른 서블릿이나 JSP로 넘겨서 추가적인 처리나 출력을 하게끔 함.

			// request.getRequestDispatcher()는 서블릿에서 다른 서블릿이나 JSP 페이지로 요청을 전달할 때 사용되는 메서드.
			// RequestDispatcher 객체를 반환합니다. 이 객체를 사용하여 다른 리소스로의 포워딩을 수행.

			// forward() 메서드를 호출함으로써 현재 서블릿의 처리 결과가 modify.jsp로 넘겨짐(현재 요청과 응답 객체를 그대로 유지한 채, 지정된 리소스로의 처리를 위임).
			// modify.jsp에서는 이전 서블릿에서 설정한 post 객체를 사용 가능.
			// => 다른 서블릿이나 JSP로의 처리를 하나의 흐름으로 연결할 수 있고, 공통적인 로직을 모듈화하여 재사용 가능.
	}
}