package com.itwill.jsp1.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MvcServlet  
 * 1. 서블릿 클래스 선언부에서 @사용. 
 * 2. web.xml에서 정의 가능(이클립스 자동설정)
  <servlet>
    <description></description>
    <display-name>MvcServlet</display-name>
    <servlet-name>MvcServlet</servlet-name>
    <servlet-class>com.itwill.jsp1.controller.MvcServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>MvcServlet</servlet-name>
    <url-pattern>/mvc</url-pattern>
  </servlet-mapping>  
  web.xml에서 만든 서블릿 주석 처리 -> 서버 수동 재시작
 */
// WAS 서블릿 호출
@WebServlet(name = "mvcServlet", urlPatterns = { "/mvc" })
public class MvcServlet extends HttpServlet { // class MvcServlet: "/mvc"의 Get 방식의 요청 처리.
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 
   *  mvc를 활용한 웹 어플리케이션 구조
	 *  model 1: JSP에서 요청처리 및 뷰 생성: 요청 처리 코드와 뷰 생성 코드가 뒤섞여 코드 복잡.
	 *  model 2: 클라이언트에서 요청 보내면, 모든 요청을 servlet이 먼저 처리, 요청 처리 후 결과를 보여줄 JSP로 이동
	 *  container: WAS(tomcat) 전체를 두름. 
	 *  database: webapplication 실행을 위해선, 오라클과 tomcat 모두 실행되어야.
	 *  
	 *  dao: repository 계층 담당. db와 관련된 crud
	 *  서블릿마다 url 매핑을 만듦. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    LocalDateTime now =  LocalDateTime.now();
	    Timestamp ts = Timestamp.valueOf(now); 
	    
	    request.setAttribute("now", ts); // 이름을 "now"라 주고, 실제로 넘겨준 건 ts. JSP에서는 "now" 변수로 el로 사용 가능. JSP의 태그들과 el을 통해서 html을 만들어냄.
	    // 동일한 어플리케이션(동일 프로젝트)에 있는 다른 페이지에서 가져다 사용할 수 있도록 데이터를 등록하는 메서드
	    // 등록하는 데이터 이름 지정, 등록했던 이름으로 검색하여 이름과 일치하는 값을 추출. 실제로 공유하기 위해 등록하는 데이터 
	    // 요청 재지정: 클라이언트가 요청한 페이지가 실행되다가 다른 페이지로 이동하는 것. 즉 서버에 존재하는 다른 자원으로 요청을 재지정하는 것. 
	    // 요청 재지정 기능을 제공하는 2가지 객체 : HttpServletResponse, RequestDispatcher 객체 
	    request.getRequestDispatcher("/WEB-INF/view.jsp").    // view.jsp 생성하기.
	    forward(request, response); // request를 JSP로 포워딩! 전달받은 request와 response 객체 그대로 view에 전달
	}

}