package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Servlet implementation class FirstServlet
 */
public class FirstServlet extends HttpServlet {
    // src/main/webapp/WEB-INF/web.xml에서 요청 주소 매핑이 되어 있음.
    // web.xml: 서버가 사용하는 환경 설정 정보
    // -> http://localhost:8081/jsp1/ex1 요청에 매핑된 서블릿 클래스.
    
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        //super(); // 부모 클래스 생성자 명시적 호출 -> 기본생성자에서는 그닥....필요 없음.
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이어트에서 GET 방식의 요청이 왓을 때, WAS(Web Application Server, Tomcat)가 호출하는 메서드.
	    // 파라미터 request: 클라이언트가 보낸 요청에 대한 정보를 가지고 있는 객체.
	    // 파라미터 response: WAS가 클라이언트로 보낼 응답을 작성하기 위해 필요한 기능들을 가지고 있는 객체
	    
	    LocalDateTime curTime = LocalDateTime.now(); // 서버의 현재 시간.
	    
	    PrintWriter writer = response.getWriter();//.append("Served at: ").append(request.getContextPath()); //ContextPath: jsp1,  이 메서드가 html을 만들고 있음.
	    writer.append("<!doctype html>")
	        .append("<html>")
	        .append("  <head>")
	        .append("     <meta charset='UTF-8' />")
	        .append("     <title> Servlet 1</title>")
	        .append("  </head>")
	        .append("  <body>")
	        .append("    <h1>첫번째 Servlet</h1>")
	        .append("    <h2>")
	        .append(curTime.toString())
	        .append("    </h2>")
	        .append("  </body>")
	        .append("</html>");
	
	}
	// 서버인 tomcat이 main을 가지고 있음.
	// tomcat에서 실행할 수 있도록 클래스와 메서드를 정의를 해줌. 그래서 웹 메인은 자바에서는 존재한지 않음.
	// 클라이언트에서 오는 모든 요청는 (웹 애플리케이션 서버 - 톰캣)은 요청을 보고 매핑된 그 클래스(/ex1)를 찾아감. 
	// 매핑된 url(/ex1)은 web.xml에 존재함.
	// request: 요청(클라이언트 -> 브라우저), response: 응답(서버-> 클라이언트)

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 // 클라이어트에서 POST 방식의 요청이 왓을 때, WAS(Web Application Server, Tomcat)가 호출하는 메서드.
	    doGet(request, response);
	}

}
