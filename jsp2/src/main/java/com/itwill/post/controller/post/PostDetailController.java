package com.itwill.post.controller.post;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.Post;
import com.itwill.post.service.PostService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// 클라이언트: 구글. 요청처리: servlet(controller), jsp를 doget호출, 응답:client(jsp)
/**
 * Servlet implementation class PostUpdateController
 */
// /post/detail을 처리하는 urlmapping. @을 사용해서 처리함. 클릭: doget방식. @임포트 했지만 이클립스 못 만들어줌. 이클립스 버그. 대신에 xml 파일에 만들어줌. 
@WebServlet(name="postDetailController", urlPatterns= {"/post/detail"}) // name: 문자열, urlPatterns: { } 비어있는 문자열
public class PostDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostDetailController.class);   // 로거 객체 만들 때 클래스 이름 줌
    private final PostService postService = PostService.getInstance(); 
    // 서비스로 보내야하니깐! static 메서드만 클래스에서 호출. 나머지는 생성된 객체에서 변수 호출. 
    // singleton 가져오는 메서드 getInstance()만 클래스 이름(static method). 그 외는 변수이름으로 호출 

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	       log.info("doGet()");  // 클라이언트의 요청 주소 안에는 쿼리 있음. id 있겠지..
//	       String s = request.getParameter("id"); // name 복붙, 서비스, dao 에 보내야함. oracle에서 확인해야하기 때문. s: id 값! "6" -> int
//	       int id = Integer.parseInt(s);
	       
	       // 1. 제목을 클릭할 때마다 바뀌는 리퀘스트 파라미터(요청파라미터) 
	       // 요청 URL에 쿼리스트링에 포함된 요청 파라미터 id(포스트 번호, PK) 값을 찾음. getParameter(): 문서 input에서 타입관계 없이 문자열을 가져옴. db에선 정수타입-> 정수 변환(select에서 제대로 사용 가능)
	       String s = request.getParameter("id");      // getParameter: 문자열만 리턴
	       long id = Long.parseLong(s); // id는 숫자 타입이어야 하기 때문에 문자열을 숫자로 변환.
//	       log.info(id + ""); 
	       log.info("id = {}", id); // id를 이용해서 글 하나 찾을 수 있음. 
	       
	      
	       
	       // TODO: DB에서 화면에 보여줄 포스트 내용을 검색
	       Post post = postService.read(id);  // <-service....!!  이 파일: controller. 오라클에서 id 값에 해당하는 모든 걸 찾고(요청). 그걸 list로 리턴받아야함(응답). 
	    
	       // TODO: JSP(뷰)에 전달 
	       request.setAttribute("post", post); // (String arg0, Object arg1) 요청을 받고 보냄(set) post 값을 변수 post 담음. list에(를?) 보냄. jsp가 post 변수를 받음 

	       
	       // 2. 뷰로 포워드 (jsp)로 감.  컨트롤러가 할 일 일단 위아래 두가지!! 
           request.getRequestDispatcher("/WEB-INF/post/detail.jsp") // 요청 받고 해당폴더로감, forward방식으로 
           .forward(request, response);  
	       
	}
	
	
}
