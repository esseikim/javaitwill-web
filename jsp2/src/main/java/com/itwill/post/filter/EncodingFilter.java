package com.itwill.post.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class EncodingFilter
 */

// Filter가 처리할 요청 주소(패턴) 설정:
/* (1) web.xml 파일에서 설정
  <filter>
    <display-name>encodingFilter</display-name>
    <filter-name>encodingFilter</filter-name>
    <filter-class>com.itwill.post.filter.EncodingFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name> 
    <url-pattern>/*</url-pattern> <- 조건이 만족 시 요청을 보낼 Controller. `/*` : 모든 Servlet 해당.
  </filter-mapping>
 */

// (2) @WebFilter 애너테이션으로 설정.
public class EncodingFilter extends HttpFilter implements Filter {

    // SLf4j 로깅 기능:
    private static final Logger log = LoggerFactory.getLogger(EncodingFilter.class);
    
    // 생성자
    public EncodingFilter () {
        log.info("Encoding Filter 생성자 호출");
    }
    
    // Filter의 라이프 사이클 메서드: destroy(), doFilter(), init() 
	/**
	 * HttpFilter가 가진 메서드. 
	 * @see Filter#destroy()
	 * 
	 */
    @Override
	public void destroy() {
    	// 서블릿 필터는 웹 애플리케이션에서 HTTP 요청 및 응답을 전처리하거나 후처리하는 작업을 수행하기 위한 기능을 제공.
    	// destroy() 메서드는 이 서블릿 필터 객체가 소멸될 때 호출되는 메서드(웹 서버가 종료될 때 실행되는 메서드).
    	// 서블릿 컨테이너가 종료되거나 웹 애플리케이션이 언로드될 때, 서블릿 필터 역시 메모리에서 제거됨. 
        // Filter 객체가 소멸될 때: 리소스 해제와 같은 작업이 필요하면 해당 작업들을 수행함.
    	// 즉, 필터 객체는 웹 서버의 생명주기에 종속되어 있음(웹 서버가 종료되면 해당 웹 애플리케이션 내의 필터 객체들도 함께 소멸됨).
        log.info("destroy() 호출");
	}

	/**
	 * HttpFilter가 가진 메서드. 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("dofilter() : chain.doFilter() 호출 전.");
        
        // 클라이언트에서 온 요청을 Controller(서블릿)에게 전달하기 전에 실행할 코드 작성.
        // 요청(request)의 인코딩 타입을 "UTF-8"로 설정:
        // 상속 관계: Request > ServletRequest > HttpServletRequest
        request.setCharacterEncoding("UTF-8");

        
		// 요청을 필터체인의 그 다음 단계로 전달 -> (다른 필터가 없이 바로 controller를 호출하는 거라면) 컨트롤러 메서드가 호출됨.(homeController.doGet() 호출)
        // Filterchain: 필터가 2개이상일 경우, 필터들이 여러개 연결되어 있는 모습. 		
		chain.doFilter(request, response); 
		// 요청을 필터체인의 그 다음 단계로 전달 -> 컨트롤러 메서드가 호출됨.
		// 필터가 1개일 경우 해당 Controller에 파라미터 전달. 2개 이상일 경우 다른 Filter에게 파라미터를 전달.
		
		
		log.info("doFilter() : chain.doFilter 호출 후");
		// 응답 보내기 직전에 코드 작성 방법도 존재함. Filter -> Controller(Servlet. forward 방식, jsp 호출) -> doFilter() -> 응답.
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
