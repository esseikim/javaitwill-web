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
 * Servlet implementation class postCreate
 */
@WebServlet(name="postCreateController", urlPatterns= {"/post/create"}) 
// tomcat이 찾을 수 있는 name. 링크 클릭시 실행될 주소
// 웹어플리케이션 서버인 tomcat이 서블릿 주소(urlPatterns)를 보고 (name으로 찾고), get/post 방식인지 보고 호출했다. 
public class PostCreateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  private static final Logger log = LoggerFactory.getLogger(PostCreateController.class);
  private final PostService postService = PostService.getInstance(); // Controller는 Service 사용.
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 링크 클릭: doGet()만 있으면 됨. `일반적`으로 링크, 버튼 클릭: form에서 post라 지정하지 않는 이상 get 방식.              
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	  System.out.println("postCreateController.doGet()"); 
	    log.info("doGet()");
//	  response.getWriter().append("Served at: ").append(request.getContextPath()); //Served at: /post
	    request.getRequestDispatcher("/WEB-INF/post/create.jsp") // 현재 요청을 /WEB-INF/post/list.jsp로 전달하기 위한 RequestDispatcher 인스턴스를 반환 
			.forward(request, response);
			// 각각의 서블릿 클래스들의 url 패턴을 보고, 활용(웹서블릿에서 매핑시킨 url주소와 일치, "/WEB-INF/post/create.jsp")
			// main만 다름(urlPatterns = {""}, "/WEB-INF/main.jsp") 
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        log.info("doPost()");
	        
	        // 요청에 포함된 요청 파라미터 정보들을 추출. 클라이언트가 보내준(브라우저 작성한) 정보들(제목, 내용, 작성자 아이디)를 추출.
	        // getParameter()의 아규먼트: form의 input(textarea)의 name 속성 값. 
	        String title = req.getParameter("title"); // name 속성에 있는 값
	        String content = req.getParameter("content"); 
	        String author = req.getParameter("author");
	        
	        Post post = new Post(0, title, content, author, null, null); // 시간 정보 필요x, 기본값 0 사용.
	        // 서비스 계층의 메서드를 호출해서 DB에 포스트를 저장. 
	        int result = postService.create(post); 
	        log.info("create result = {}", result);
	        
	        // 포스트 목록 페이지로 이동(redirect) <- 주소 바뀜. postCreate가 아닌 postList로 바뀜. jsp로 가는 게 아님.
	        resp.sendRedirect("/post/post");  // <- `/post`: contextroot, `/post`: 목록보기.  요청주소: /contextroot/path.
	        // 응답해서 클라이언트가 받게됨. 클라이언트는 다시 get 방식으로 요청을 보냄. post > redirect > get! 두개의 메서드가 jsp 불러주고, jsp가 화면에 그려 응답! 
	        
	        // PRG(Post-Redirect-Get) 패턴. get 방식: jsp로 바로 forward. post: 새로운 페이지로 아예 이동을 하는 경우. 
	        /* cf) 목록 클릭: 요청, get 방식. 메인: 테이블(번호, 제목, 작성자, 수정시간) <- 목록을 보려면 DB까지 갔다와야 함. 
	         * controller(ser), service(dao), dao(select 쿼리 실행), 받은 arraylist 리턴 : controller가 리스트 jsp에 넘겨줌. jsp가 화면에 그림. 
	         * 1. 새 글 작성: 제목, 아이디, ..입력하세요 (db까지 갔다올 필요 없이, 클릭 시 화면만 보여주기만 하면 됨. 새글 작성(get방식), jsp가 form만 만들어줌. Service까지 갈 필요 없음.
	         * 2. 작성 완료: form 안에 있음. 버튼은 요청을 보내는데, post 방식. 요청을 처리하는 컨트롤러로 감 (/post/create) 
	         * DB까지 갔다와야 함. 입력한 값을 controller를 분석. dao가 insert, 몇개의 행이 insert 되었는 지 리턴(DB 업뎃 + 목록 페이지로 redirect). 
	         */       
	}
}