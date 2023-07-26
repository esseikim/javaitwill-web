package com.itwill.post.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class HomeController: 홈컨트롤러 - 메인을 보여줌. 
 */
@WebServlet(name = "homeController", urlPatterns = {""})
// url 패턴이 굉장히 중요함! homeController는 tomcat 서버에서 호출해줌. 여기까지가 서버의 역할. 요청주소는 여러가지.. 네이버, 다음,...
// 뒤쪽 주소들이 달라질 수 있음. url에 따라서 보여지는 페이지가 달라짐. 서블릿, jsp의 역할: 요청받은 클라이언트에게 그 html을 보여줌
// 이 클래스가 무슨 요청을 처리할거냐. 비어있는 문자열: contextroot까지만 요청이 들어온 건 homecontroller가 처리하겠다. 
// "http:// localhost:8081/post/" 요청주소(context root)를 처리하는 서블릿. 
// 처리하는 url이 없는 것이 아닌, post까지만 처리하겠다는 얘기. "/"를 써버리면 다른 의미가 됨. post/abc 든 뭐든 입력하면 똑같은 페이지가 보여짐
// 모든 요청을 다 처리하는 서블릿이 되어버림. 어떤 주소가 오든. 
// 우리가 원하는 것: contextroot까지만(javaproject와 동일한 이름으로 자동생성됨) 
// 서블릿이 바뀐 내용이 웹서버에 반영이 됐다. = INFO: 이름이 [/post]인 컨텍스트를 다시 로드하는 작업이 시작되었습니다.
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("homeController.doGet() 호출");
	    // 브라우저에서 주소 입력 후 엔터(요청)만 했는데 doGet()가 호출됨. 웹어플리케이션 서버인 tomcat이 서블릿 주소를 보고 get/post 방식인지 보고 호출했다. 
	    
	    // View로 요청을 포워드: 
	    // 서블릿이 한 일: 받은 요청을 포워드(전달). 다른 사람한테 받았고, 받은 걸 다른 사람한테 그대로 전달해줌. 
	    // 받은 걸 "/WEB-INF/main.jsp" 로 보내겠다. 
	    request.getRequestDispatcher("/WEB-INF/main.jsp")
	    .forward(request, response);
	}

}
