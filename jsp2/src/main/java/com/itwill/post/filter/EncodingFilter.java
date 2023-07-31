package com.itwill.post.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class EncodingFilter
 * 
 * servlet과 마찬가지로 web.xml에도 코드가 자동 완성됨.
 */
// Filter가 처리할 요청 주소(패턴) 설정:
// (1) web.xml 파일에서 설정하거나 -> 
/*
 * </filter> <%-- 변수 선언 즉, EncodingFilter encodingFilter  --%>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern> <%-- 필터에서 조건이 맞을 경우 넘어갈 모든 controller 즉, 모든 servlet 해당. 만약 따로 호출할 controller가 있으면 해당 url을 입력하면 됨.--%>
  </filter-mapping>
 */
// (2) @WebFilter 애너테이션으로 설정할 수 있음.
public class EncodingFilter extends HttpFilter implements Filter {

    // SLf4j 로깅 기능:
    private static final Logger log = LoggerFactory.getLogger(EncodingFilter.class);
    
    // 생성자
    public EncodingFilter () {
        log.info("Encoding Filter 생성자 호출");
    }
    
    // Filter의 라이프사이클 메서드: destroy(), doFilter(), init() 
	/**
	 * HttpFilter것
	 * @see Filter#destroy()
	 * 
	 */
    @Override
	public void destroy() {
        // Filter 객체를 메모리에서 제거할 때 WAS가 호출하는 생명주기(life cycle)메서드.
        // Filter 객체가 소멸될 때: 리소스 해제와 같은 작업이 필요하면 해당 작업들을 수행함.
        // 웹 서버가 셧다운될 때 호출되는 메서드. 즉, 웹 서버가 죽어야 Filter가 없어짐.
        // 저장 후 컴파일 다시 해야하니 서버를 죽인 다음에 다시 생성함.
        // 그 때 console 창에 호출됨.
        log.info("destroy() 호출");
	}

	/**
	 * HttpFilter것
	 * 가장 중요함. + 빠지면 안 됨.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("dofilter() : chain.doFilter() 호출 전.");
        
        // 클라이언트에서 온 요청을 controller(서블릿)에게 전달하기 전에 실행할 코드들을 작성.
        // 요청(request)의 인코딩 타입을 "UTF-8"로 설정:
        // 상속 관계: Request > ServletRequest > HttpServletRequest
        request.setCharacterEncoding("UTF-8");
        // 요청이 서블릿으로 가기 전에 할 일.

        
		// 요청을 필터체인의 그 다음 단계로 전달 -> (다른 필터가 없이 바로 controller를 호출하는 거라면) 컨트롤러 메서드가 호출됨.(homeController.doGet() 호출)
        // -> 필터가 여러개일 경우도 존재.
		chain.doFilter(request, response); 
		// Filterchain: (필터가 2개이상일 경우) 필터들이 여러개 연결 되어있는 모습
		//				 필터가 1개 일경우 controller에게 해당 파라미터를 전달.
		//                    2개 일경우 다른 Filter에게 해당 파라미터를 전달.
		
		// Filter -> servlet + forward 방식 jsp 호출 -> doFilter -> 응답.
		
		log.info("doFilter() : chain.doFilter 호출 후");
		// 응답 보내기 직전으로 코드를 작성해도 됨.
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		// 필터 객체가 생성된 후 초기화(intilalization) 작업이 필요할 때 WAS가 호출하는 생명주기 메서드.
        
        log.info("init() 호출");
	}

}
