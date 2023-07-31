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
 * 서블릿 클래스:  get방식의 요청을 처리함. 어떤 주소의 get방식을 처리할거냐. 
 * 요청주소 세팅: 서블릿 클래스마다. 위치는 두군데 가능. 
 * ex4 만 하면 됨. 매핑한다는 얘기. 
 * 1. 클래스 선언부에서 @사용. 
 * 2. web.xml에서 정의 가능(이클립스 자동설정)
  <servlet>
    <description></description>
    <display-name>MvcServlet</display-name>
    <servlet-name>MvcServlet</servlet-name>
    <servlet-class>com.itwill.jsp1.controller.MvcServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>MvcServlet</servlet-name>
    <url-pattern>/MvcServlet</url-pattern>
  </servlet-mapping>  
  web.xml에서 만든 서블릿 제거. 
  서버 재시작해야함. 서블릿 클래스 만들고 나서 바로 반영 안됨. 
   MvcServlet: 요청 처리! 
   JSP에서 HTML 만들어내자. 
 */
@WebServlet(name = "mvcServlet", urlPatterns = { "/mvc" })
public class MvcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 폴더, 파일이름. 
	 * request 디스패치를 가져올 때 forward() 호출. 전달받은 request와 response를 그대로 넣어주면 됨. 
	 * 요청이 들어와서, was 서블릿 호출, jsp를 만들겠다(view). 아직까지 jsp가 만들지 않은 상태. 못 찾겠다. 
	 * "/WEB-INF/view.jsp" 만 만들면 됨. 
	 *  모든 자바 코드는 여기서 적은 후 jsp로 forward하면 jsp에서는 화면만 그려주면 됨. jsp는 화면 코드만 작성하게 됨. 
	 *  ldt, ts 만들고 request에 set attribute함. 
	 *  request를 JSP로 포워딩!      request.getRequestDispatcher("/WEB-INF/view.jsp").
	 *  jsp에서는 now 변수를 el로 사용 가능함. (jsp의 태그들과 el을 통해서 html을 만들어냄.)
	 *  
	 *  db에서 select한 코드를 가져오면 arraylist 만들 수 있음. 그 list를 setattribute하면 됨. 
	 *  
	 *  model 1: jsp에서 요청처리 및 뷰 생성처리: 요청 처리 코드와 뷰 생성코드가 뒤섞여 코드가 복잡함. 
	 *  mvc를 활용한 웹 어플리케이션 구조
	 *  model 2: 클라이언트에서 요청 보내면, 모든 요청을 servlet이 먼저 처리, 요청 처리 후 결과를 보여줄 JSP로 이동
	 *  container: WAS(tomcat) 전체를 두른다. 
	 *  database: webapplication 실행을 위해선, 오라클과 tomcat 모두 실행되어야.
	 *  
	 *  dao: repository 계층 담당. db와 관련된 crud
	 *  서블릿마다 url 매핑을 만듦. 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    LocalDateTime now =  LocalDateTime.now();
	    Timestamp ts = Timestamp.valueOf(now); 
	    
	    request.setAttribute("now", ts); // 이름을 now라 주고, 실제로 넘겨준 건 ts. 
	    // 동일한 어플리케이션에 있는 다른 페이지에서 가져다 사용할 수 있도록 데이터를 등록하는 메서드
	    // 등록하는 데이터 이름 지정, 등록했던 이름으로 검색하여 이름과 일치하는 값을 추출. 실제로 공유하기 위해 등록하는 데이터 
	    // 요청재지정: 클라이언트가 요청한 페이지가 실행되다가 다른 페이지로 이동하는 것. 즉 서버에 존재하는 다른 자원으로 요청을 재지정하는 것. 
	    // 요청재지정 기능을 제공하는 2가지 객체 : HttpServletResponse, RequestDispatcher 객체 
	    request.getRequestDispatcher("/WEB-INF/view.jsp").  
	    forward(request, response);
	}

}
