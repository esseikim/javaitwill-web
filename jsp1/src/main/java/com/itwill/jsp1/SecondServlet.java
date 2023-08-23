package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SecondServlet
 */
// jsp와 Servlet이 다른 이유가 Servlet의 경우 Servers 탭에서 서버 재시작을 해야 작동.
// jsp의 추가일 경우에는 상관이 없지만 새로운 자바클래스가 추가되었을 경우 서버 수동 재시작
// Servlet이 작성이 되어있어도 web.xml에 해당 파일이 매핑이 되어있지 않으면 Servlet 클래스를 호출할 수 없음.
// 다음 설정 방식은 2가지가 존재함. (2가지 중 하나만 사용)
// 서블릿 클래스의 URL 매핑(요청 주소)을 설정하는 방법:
// 1. web.xml 설정 파일에서 설정하거나, -> 이클립스가 자동으로 설정.
// 2. 서블릿 클래스에서 @WebServlet 애너테이션(설정값 ','로 구분)으로 설정하는 방법이 있음. -> import 필수.
// (주의) web.xml과 애너태이션을 모두 설정하면 안됨.
@WebServlet(name = "sercondServlet", urlPatterns = { "/ex2" })
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; // 해당 클래스의 직렬화 버전을 1로 설정

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 클라이언트로 보내는 응답(response)의 한글이 깨지지 않도록 하기 위해서.
	    // response.getWriter() 메서드를 호출하기 전에 컨텐트 타입을 세팅.
	    // 확장자가 html인 text파일, 인코딩: charset=UTF-8
	    response.setContentType("text/html; charset=UTF-8");
		
	    
	    PrintWriter writer = response.getWriter(); // writer 객체를 사용하여 HTML 코드를 작성하고, 이를 응답으로 클라이언트로 전송
			writer.append("<!doctype html>") // PrintWriter 객체에 텍스트를 추가. write(), print(), append() 모두 텍스트를 추가할 수 있는 방법으로 전부 사용 가능.
		    .append("<html>")
		    .append("<body>")
		    .append("<h1>두번째 Servlet</h1>")
		    .append("<a href='./'>인덱스 페이지</a>")
		    .append("</body>")
		    .append("</html>");
	}

}