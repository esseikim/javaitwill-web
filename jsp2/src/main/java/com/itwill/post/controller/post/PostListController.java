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


/** 컨트롤러는 서블릿! 
 * Servlet implementation class controller  
 * xml 해석: name = "postListController"는 /postlistcontroller 요청주소를 처리하는 거다. 
 * 이클립스가 자동으로 만들어주는 서블릿과 서블릿매핑(web.xml)까지는 지워주고 @WebServlet -> 요청주소만 /post로 바꿔주면 됨.
 * 중요한 것, name, 더 중요: urlpatterns! 
 * 이름은 비어있는 문자열로 남겨주면 안 됨.(서버 실행 안 됨.) 첫글자만 소만자로 바꾸기. 
 * 이름은 다른 서블릿과 겹치면 안됨. 클래스 이름 보통 겹치게 안 만듦. 대문자만 소문자로 바꿔주기 
 * 어떤 url이 처리되는 지 톰캣에게 알려줘야함. 이 @는 톰캣에게 알려주기 위함.
 * 이 서블릿은 이런 주소로 들어오는 요청을 처리하는 것이다. 요청이면 doget, dopost 방식이면 
 * 똑같은 주소의 메서드만 다르게 톰캣이 호출해줌. 
 * 서버에 반영되기 전인 클래스. 서버에 한번 올려주면 이클립스는 변경사항이 생길 때마다 로그가 올라옴(서블릿)
 * - 링크를 클릭하면 바뀌는 주소가 urlpatterns  
 * 새로고침: 클라이언트가 서버에 요청을 다시 보냈다. 서버는 url, get/post방식을 파악할 수 있음.
 * 어떤 메서드를 호출해야할 지 알 수 있음. 
 * WAS web application server, tomcat
 * 요청: post를 보겠다. 받는 것: was. was가 controller호출(doget호출). jsp가 html을 만들고, 클라이언트로 response를 보내겠다.
 */
@WebServlet(name = "postListController", urlPatterns = {"/post"}) // `/post`를 처리하는 controller
public class PostListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostListController.class); // 로그를 이용할 수 있는 파이널 변수 추가
	private final PostService postService = PostService.getInstance(); // 서비스를 이용할 수 있는 파이널 변수 추가
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override  // doGet() 호출: tomcat. request 객체를 argument로 넘겨줌: tomcat
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	  System.out.println("PostListController.doGet()");
	    log.info("doGet()"); // 자동으로 log 찍혀서 클래스 이름 안 적어도 됨. 
	    
	    // 서비스 계층의 메서드를 호출해서 포스트 목록을 불러옴. 
	    List<Post> posts = postService.read(); // 리스트 받아서 jsp에 넘겨줌.
	    
	    // 포스트 목록을 jsp에게 전달. Post 객체 저장하면 el에서 이 "posts" 변수를 쓸 수 있음. el에서 테이블 그리기만 하면 됨.
	    // request의 속성으로 arraylist 세팅 후 `list.jsp`에게 전달. 
	    request.setAttribute("posts", posts);   // 변수, value
	    
	    // request.getWriter().append("Served at:").append(request.getContextPath()); // html 만듦
	    request.getRequestDispatcher("/WEB-INF/post/list.jsp")  // 설정된 파일에 전달받은 request와 response가 그대로 포워딩 됨. 
	    .forward(request, response); 
	    // INFO: 이름이 [/post]인 컨텍스트를 다시 로드하는 것을 완료했습니다. ?? 변경사항이 자동으로 서버에 반영됨.(이미 만들어진 것 이클립스가). 새로 만들어진 것은 반영이 안됨.  
	    // 내가 html 만들지 않을 거다. 전달. 
	    // controller는 잘 호출했는데 jsp 파일을 안 만들어서 파일이 없어서 404 뜸. 
	    // jsp는 새로 만들어졌을 땐 서블릿과 다르게 자동 반영. 서블릿만 새로 만들었을 때 서버에 한번 더 로드해줄 필요가 있음(수동 재시작) 
	}
/*
 * 요청 들어옴 was가 받고, 요청 방식, 주소를 분석해서 그것에 따른 controller를 찾아 controller의 메서드를 호출함. 
 * 포워딩, 포워딩의 결과가 response로 나와서 우리의 브라우저(화면)에 결과가 보여짐 
 */


}