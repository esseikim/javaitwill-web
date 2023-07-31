package com.itwill.post.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(
        filterName = "authenticationFilter",
        urlPatterns = {"/post/create", "/post/detail", "/post/modify", "/post/upadate", "/post/delete"})
// 로그인이 필요한 기능 (요청주소들)들을 urlPatterns에 설정. 
// 하나의 서블릿은 하나의 요청주소만 처리하고 있었음. 지금까지는. 필터도 마찬가지. 하나의 필터가 두개 이상의 요청주소 처리 가능
// 목록페이지(/post/post). contextroot까지 제외하고 그 뒤쪽 주소 지정하면 됨.

//urlPatterns에 설정된 요청 주소들에 대해서, 
// 로그인이 되어 있으면, 요청을 계속해서 처리(controller에게 요청을 전달)
// 로그인이 되어 있지 않으면, 로그인 페이지로 이동(redirect).
public class AuthenticationFilter extends HttpFilter implements Filter {
       private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class); 


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
       @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // FilterChain이라는 객체를 전달받음
	        throws IOException, ServletException {
           log.info("doFilter");
           
           // 로그인이 되어 있는 지를 체크: 
           // (1) request(요청 객체)에서 session을 찾음.  HttpSession -> rq객체도 Http객체여야.
           HttpSession session = ((HttpServletRequest) request).getSession();  // getSession이 HttpServletRequest에 속해져 있음.
           // 요청 객체에서 session을 찾음! 
           // 서버가 클라이언트에 쿠키 보내줌(쿠키 아이디에 세션을 보내줌). 리퀘스트에 있는 쿠키아이디를보고......
           
           
           // (2) 세션에 로그인 정보(signedInUser)가 있는 지를 확인. <- signedInUser(변수이름). null이 아닌 경우: 로그인. 
          String username = (String) session.getAttribute("signedInUser") ; 
          // 세션에 저장할 때 해당 변수로 저장했으니 가져올 때도 동일한 변수로.
          //      저장할 수 있는 데이터들은 어떤 타입이든지 가능하니 Object를 반환.
          log.info("로그인 정보: {}", username);
          
         if(username != null) { // "signedInUser" 로그인 정보가 세션(저장객체)에 저장된 경우, 요청을 필터체인 순서대로 전달한다. 
             
             // pass the request along the filter chain: 들어온 요청을 필터체인에 따라서 해당 controller에게 보내준다.(로그인 여부에 따라) 
             chain.doFilter(request, response); 
                 return;
         }
         
         // 로그인 정보가 없으면, 로그인 페이지로 redirect (contextroot 같이 써줘야. sendRedirect나 getRequestDispatcher 동일). 필터 클래스 생성 -> 서버 재시작 
         ((HttpServletResponse)response).sendRedirect("/post/user/signin");
         // controller 이전에 필터에서 로그인, 로그아웃 체크함. controller 마다 이런 기능 만들 필요 없이. 
         
	}
}
