package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ForwardServlet
 * ForwardServlet: 서블릿 인터페이스를 구현하거나 서블릿을 확장하여 작성됨. 서블릿은 클라이언트의 요청을 처리하고 응답을 생성하는 Java 클래스
 * Servlet implementation class: 해당 클래스가 서블릿의 구현체임을 나타냄
 */
// @WebServlet 어노테이션: 서블릿의 매핑 및 설정을 보다 편리하게 처리하기 위해 사용됨. 기존에는 web.xml 파일에서 서블릿 매핑을 설정해야 했지만, 
// 어노테이션을 사용하면 서블릿 클래스 내부에 매핑 정보를 포함시킬 수 있음
@WebServlet(name = "forwardServlet", urlPatterns = { "/ex3" }) // '/' 주의, 해당 서블릿 클래스를 "/ex3" URL 패턴에 매핑하라는 의미. 클라이언트가 "/ex3" 경로로 요청을 보내면 이 클래스의 doGet() 또는 doPost() 메서드가 실행되어 해당 요청을 처리
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; // 해당 클래스의 직렬화 버전을 1로 설정

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    System.out.println("ForwardServlet#doGet() 호출");

	    /*
	     *  HttpServletRequest: 클라이언트가 서버에 보내는 요청정보를 처리하는 객체
	     *  HttpServletResponse: 서버가 클라이언트로 보내는 응답정보를 처리하는 객체
	     */
	    // servlet에서는 요청이 들어온 것을 jsp로 전달(응답)함.
	    
	    // 클라이언트에서 서버로 온 요청을 새로운 페이지로 "forward" 방식으로 이동:
	    // 같은 WAS(Web Application Server)의 같은 웹 애플리케이션 안에서만 페이지 이동이 가능.
	    // -> 같은 WAS: tomcat, 같은 웹 애플리케이션: 같은 프로젝트.
	    // 다른(웹) 서버 또는 다른 웹 애플리케이션의 페이지로 forward 이동을 할 수 없음.
	    
	    // 요청 주소(URL) 변경x.
	    // -> jsp가 보내준 정보를 보고 있음. 그런데 주소는 servlet.
	    // -> servlet이 없어도 jsp가 단독으로 처리 가능함.
	    // forward: 최초 요청 주소가 그대로 남아있으면서. 응답을 보내주는 파일/페이지가 달라짐.
	    // 즉, 최초의 request 객체와 response 객체가 유지됨.
	    //     => tomcat에게서 전달받은 argument를 그대로 forward의 example.jsp에 넘겨줌.
	    //         -> example.jsp에서 동일한 argument를 볼 수 있음.
	    
	    // src/main/webapp 폴더 아래의 파일 경로와 파일 이름을 사용. 
	    // 요청을 재지정할 대상에 대한 정보를 path 형식, name 등 어떤 것으로 지정하는가만 다를 뿐, RequestDispatcher 객체를 추출하는 기능은 같음 
	    request.getRequestDispatcher("example.jsp").forward(request, response);
	    // 객체 추출, forward 메서드를 통해 현재의 요청(request)과 응답(response) 객체를 그대로 전달
		// ForwardServlet에서 처리된 요청 정보와 응답 정보가 "example.jsp" JSP 파일로 전달
		// "example.jsp" 파일은 해당 서블릿을 거치지 않고 바로 클라이언트로 전송("JSP가 단독으로 처리 가능하다"는 의미)
		// ForwardServlet의 중간에서 요청과 응답을 처리하고 있긴 하지만, 최종적으로 클라이언트에게 제공되는 내용은 "example.jsp"의 내용

		// 서블릿과 JSP가 함께 사용될 때, 서블릿은 비즈니스 로직을 처리하고 JSP는 사용자 인터페이스를 작성하고 동적인 콘텐츠를 표현하는 역할을 담당하게 됨.
	}

}