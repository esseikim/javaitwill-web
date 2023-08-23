package com.itwill.post.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeController: 메인페이지. 
 */
@WebServlet(name = "homeController", urlPatterns = {""})
// homeController: tomcat 서버에서 호출해줌. 여기까지가 서버의 역할. 요청주소는 여러가지.. 네이버, 다음, ...
// url에 따라서 보여지는 페이지가 달라짐(서블릿), jsp: 요청받은 클라이언트에게 해당 html을 보여줌.
// 비어있는 문자열: contextroot까지만 요청이 들어온 건 homecontroller가 처리. 
// "http:// localhost:8081/post/" 요청주소(context root)를 처리하는 서블릿. 
// 처리하는 url이 없는 것이 아님. `/post`까지만 처리하겠다는 얘기. "/": `post/abc` 뭐를 입력하든 동일한 페이지가 보여짐(다른 의미). 어떤 주소가 오든 모든 요청을 다 처리하는 서블릿이 되어버림.  
// 우리가 원하는 것: contextroot까지만!(project명과 동일한 이름으로 자동 생성됨.) 
// INFO: 이름이 [/post]인 컨텍스트를 다시 로드하는 작업이 시작되었습니다 < 서블릿의 바뀐 내용이 웹서버에 반영됨을 의미
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("homeController.doGet() 호출");
	    // 브라우저에서 주소 입력 후 엔터(요청)만 했는데 doGet()이 호출됨. 웹어플리케이션 서버인 tomcat이 서블릿 주소, get/post 방식 인지, 호출. 
	    
	    // View로 요청을 포워드: 
	    // 서블릿이 한 일: 받은 요청을 포워드(전달). 다른 사람한테 받았고, 받은 걸 다른 사람한테 그대로 전달. 
	    // 받은 걸 "/WEB-INF/main.jsp" 로 보내겠다. 
	    request.getRequestDispatcher("/WEB-INF/main.jsp")
	    .forward(request, response);
	}

}