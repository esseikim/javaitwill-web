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
@WebFilter(filterName = "authenticationFilter", urlPatterns = { "/post/create", "/post/detail", "/post/modify",
		"/post/upadate", "/post/delete" })
		// 로그인이 필요한 요청주소를 urlPatterns에 설정.
		// 두개 이상의 요청주소 처리 가능, 동일하게 contextroot 제외하고 주소 지정.

// 설정된 요청 주소들에 대해, 로그인이 되어 있으면, 요청을 계속해서 처리(Controller에 요청을 전달).
// 로그인이 되어 있지 않으면, 로그인 페이지로 이동(redirect).
public class AuthenticationFilter extends HttpFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // FilterChain 객체를 전달 받음.
			throws IOException, ServletException {
		log.info("doFilter");

		// 로그인이 되어 있는 지를 체크:
		// (1) request(요청 객체)에서 Session을 찾음. HttpSession -> request 객체도 Http 객체여야 함.
		HttpSession session = ((HttpServletRequest) request).getSession(); // HttpServletRequest로부터 getSession(요청 객체에서 Session을 찾음).


		// (2) 세션에 로그인 정보(signedInUser) 존재 여부 확인.  
		String username = (String) session.getAttribute("signedInUser"); // 세션에 저장할 때 해당 변수로 저장했으니 가져올 때도 동일한 변수 적용. 저장 가능한 데이터들은 어떤 타입이든 가능(Object를 반환).
		log.info("로그인 정보: {}", username);

		if (username != null) { // "signedInUser" 로그인 정보가 세션(저장 객체)에 저장된 경우, 요청을 필터 체인 순서대로 전달.

			// pass the request along the filter chain: 필터체인에 따라서 해당 Controller에 요청 전달. 
			chain.doFilter(request, response);
			return;
		}
		
		((HttpServletResponse) response).sendRedirect("/post/user/signin"); // 로그인 정보가 없으면, 로그인 페이지로 redirect (.request/ .redirect: contextroot 포함한 경로 작성).
		// Controller 마다 기능 만들 필요 없이 Controller 이전에 필터에서 로그인, 로그아웃 체크. 
		// 필터 클래스 생성 -> 서버 재시작

	}
}
